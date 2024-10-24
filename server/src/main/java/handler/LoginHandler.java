package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.ErrorMessages;
import model.UserData;
import model.UsernameAndPassword;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    private final LoginService loginService;


    public LoginHandler(LoginService loginService) {
        this.loginService = loginService;
    }

    public Object loginUser(Request req, Response res) throws DataAccessException {
        UsernameAndPassword usernameAndPassword = new Gson().fromJson(req.body(), UsernameAndPassword.class);

        ErrorMessages attempt = new ErrorMessages("Error: unauthorized");
        try {
            res.status(200);
            AuthData authData = loginService.loginUser(usernameAndPassword);
            return new Gson().toJson(authData);
        } catch (DataAccessException g){
            String errorMessage = g.getMessage();
            if (errorMessage.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(attempt);
            } else {
                res.status(500);
            }
        }
        return new Gson().toJson(attempt);
    }

}
