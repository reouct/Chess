package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import model.UserData;
import org.eclipse.jetty.server.Authentication;


public class RegisterService {
    private final UserDAO userDao;

    public RegisterService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void register(Authentication.User user) throws DataAccessException {
    userDao.createUser(new UserData("username", "password", "email"));
    }
}