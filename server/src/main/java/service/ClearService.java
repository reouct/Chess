package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;


public class ClearService {

    static UserDAO userDao;
    static GameDAO gameDao;
    static AuthDAO authDao;

    public ClearService(UserDAO userDao, GameDAO gameDao, AuthDAO authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }
    public static void clear() throws DataAccessException {
        gameDao.clear();
        authDao.clear();
        userDao.clear();
    }
}
