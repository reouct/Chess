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

        // 400 bad request
        if (newUser.password() == null || newUser.username() == null || newUser.email() == null){
            res.status(400);
            return "error";
        }

        // Register New user
//        try {
//            registerService.registerUser(newUser);
//            res.status(200);
//            String authToken = registerService.createAuthToken(newUser);
//            return new Gson().toJson(new AuthData(authToken, newUser.username()));
//        } catch (DataAccessException e){
//            String errorMessage = e.getMessage();
//            if (errorMessage.equals("Error: already taken")) {
//                res.status(403); // 403 already taken
//            } else {
//                res.status(500);
//            }
//            return "Error";
//        }
        registerService.registerUser(newUser);
        res.status(200);
        String authToken = registerService.createAuthToken(newUser);
        return new Gson().toJson(new AuthData(authToken, newUser.username()));

    }
}
