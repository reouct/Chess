package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.ErrorMessages;
import model.UsernameAndPassword;
import service.LogoutService;
import spark.Request;
import spark.Response;

import javax.xml.crypto.Data;
import java.io.Reader;

public class LogoutHandler {
    private final LogoutService logoutService;

    public LogoutHandler(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    public Object logoutUser(Request req, Response res) throws DataAccessException {
        String authData = req.headers("authorization");
        ErrorMessages attempt = new ErrorMessages("Error: unauthorized");

        try {
            logoutService.logout(authData);
            res.status(200);
            return"{}";
        } catch (DataAccessException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(attempt);
            } else {
                res.status(500);
            }
        }
        return "Error";

    }
}
