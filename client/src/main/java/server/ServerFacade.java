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

    public String getAuthToken() {
        return authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setAuthToken(String auth) {
        authToken = auth;
    }

    public void setUsername(String user) {
        username = user;
    }

    private boolean doesOutput(String type) {
        return type.equals("POST") || type.equals("PUT");
    }

    public <T> T makeRequest(String type, String urlString, Object request, String authToken, Class<T> responseClass) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URI(urlString).toURL();
            connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod(type);
            connection.setDoOutput(doesOutput(type));
            connection.setRequestProperty("Content-Type", "application/json");

            if (authToken != null) {
                connection.setRequestProperty("Authorization", authToken);
            }

            // Send request body if applicable
            if (doesOutput(type)) {
                try (OutputStream requestBody = connection.getOutputStream()) {
                    String json = new Gson().toJson(request);
                    requestBody.write(json.getBytes());
                    requestBody.flush();
                }
            }

            // Get response
            try (InputStream responseBody = (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    ? connection.getInputStream()
                    : connection.getErrorStream();
                 InputStreamReader reader = new InputStreamReader(responseBody)) {

                return new Gson().fromJson(reader, responseClass);
            }
        } catch (URISyntaxException e) {
            throw new IOException("Invalid URL format", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
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
            return makeRequest("POST", urlString + "/user", registerRequest, null, AuthResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthResult login(LoginRequest loginRequest) {
        try {
            return makeRequest("POST", urlString + "/session", loginRequest, null, AuthResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
