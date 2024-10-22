package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import model.AuthData;
import model.UserData;


public class RegisterService {
    private final UserDAO userDao;
    private final AuthDAO authDao;

    public RegisterService(UserDAO userDao, AuthDAO authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public AuthData registerUser(UserData user) throws DataAccessException{
        if(userDao.getUser(user.username()) != null ){
            throw new DataAccessException("Error: already taken");
        }
        userDao.createUser(user);
        String authtoken = authDao.createAuth(user.username());
        return new AuthData(authtoken, user.username());
    }
}