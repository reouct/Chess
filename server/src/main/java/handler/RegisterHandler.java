package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    private final RegisterService registerService;

    public RegisterHandler(RegisterService registerService) {
        this.registerService = registerService;
    }

    public Object registerUser(Request req, Response res) throws DataAccessException {

        UserData newUser = new Gson().fromJson(req.body(), UserData.class);
        registerService.registerUser(newUser);
        res.status(200);
        String authToken = registerService.createAuthToken(newUser);
        return new Gson().toJson(new AuthData(authToken, newUser.username()));
        //return "Success";

    }
}
