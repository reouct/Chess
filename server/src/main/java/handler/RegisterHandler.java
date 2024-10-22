package handler;

import com.google.gson.Gson;

import dataaccess.DataAccessException;
import model.AuthData;
import model.ErrorMessages;
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

        // 400 bad request
        if (newUser.password() == null || newUser.username() == null || newUser.email() == null){
            res.status(400);
            ErrorMessages attempt = new ErrorMessages("Error: bad request");
            return new Gson().toJson(attempt);
        }


        ErrorMessages attempt = new ErrorMessages("Error: already taken");
        // Register New user
        try {
            AuthData authData = registerService.registerUser(newUser);
            res.status(200);
            return new Gson().toJson(authData);
        } catch (DataAccessException e){
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Error: already taken")) {
                res.status(403);// 403 already taken
                return new Gson().toJson(attempt);
            } else {
                res.status(500);
            }
            return "Error";
        }

    }
}
