package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import org.eclipse.jetty.server.Authentication;


public class RegisterService {
    private final UserDAO userDao;

    public RegisterService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void register(Authentication.User user) throws DataAccessException {

    }
}