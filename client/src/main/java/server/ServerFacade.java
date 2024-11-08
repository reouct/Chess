package server;

import com.google.gson.Gson;
import request.LoginRequest;
import request.RegisterRequest;
import result.AuthResult;
import result.Result;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ServerFacade {
    private String authToken;
    private String username;
    private final String urlString;

    public ServerFacade(int port) {
        this.urlString = "http://localhost:" + port;
    }

    private boolean doesOutput(String type) {
        return type.equals("POST") || type.equals("PUT");
    }

    public <T> T makeRequest(String type, String urlString, Object request, String authToken,
                             Class<T> responseClass) throws IOException {
        try {
            URL url = new URI(urlString).toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod(type);
            connection.setDoOutput(doesOutput(type));

            if (authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            connection.setRequestProperty("Content-Type", "application/json");

            connection.connect();

            if(doesOutput(type)) {
                try (OutputStream requestBody = connection.getOutputStream();) {
                    String json =  new Gson().toJson(request);
                    requestBody.write(json.getBytes());
                    requestBody.flush();
                }
            }

            InputStream responseBody;
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                responseBody = connection.getInputStream();
            }
            else {
                responseBody = connection.getErrorStream();
            }
            InputStreamReader reader = new InputStreamReader(responseBody);

            return new Gson().fromJson(reader, responseClass);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Result clear() {
        try {
            return makeRequest("DELETE", urlString + "/db", null, null, Result.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthResult register(RegisterRequest registerRequest) {
        try {
            makeRequest("POST", urlString + "/user", registerRequest, null, AuthResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public AuthResult login(LoginRequest loginRequest) {
        try {
            makeRequest("POST", urlString + "/session", loginRequest, null, AuthResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
