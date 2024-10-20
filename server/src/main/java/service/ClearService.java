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
        ClearService.userDao = userDao;
        ClearService.authDao = authDao;
        ClearService.gameDao = gameDao;
    }
    public static void clear() throws DataAccessException {
        gameDao.clear();
        authDao.clear();
        userDao.clear();
    }
}

