package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
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

//    public String loginUser(Request req, Response res) throws DataAccessException {
////        UsernameAndPassword usernameAndPassword = new Gson().fromJson(req.body(), UsernameAndPassword.class);
////        String username = usernameAndPassword.username();
////        String password = usernameAndPassword.password();
//        //UserData storeDate = loginService.findDataByUnPw(username, password);
//
//    }
}
