package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import model.UserData;


public class RegisterService {
    private final UserDAO userDao;
    private final AuthDAO authDao;

    public RegisterService(UserDAO userDao, AuthDAO authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public void registerUser(UserData user) throws DataAccessException{
        userDao.createUser(user);
    }

    public String createAuthToken(UserData newUser) {
        return authDao.createAuth(newUser);
    }
}