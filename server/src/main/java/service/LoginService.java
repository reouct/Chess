package service;

import dataaccess.interfaces.UserDAO;
import model.UserData;
import spark.Request;
import spark.Response;

public class LoginService {
    private final UserDAO userDao;


    public LoginService(UserDAO userDao) {
        this.userDao = userDao;
    }

//    public UserData findDataByUnPw(String username, String password) {
//        //return userDao.
//    }


}
