package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.AuthDAO;


public class ClearService {

    private final UserDAO userDao;
    private final GameDAO gameDao;
    private final AuthDAO authDao;


    public ClearService(UserDAO userDao, GameDAO gameDao, AuthDAO authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }
    public void clear() throws DataAccessException {
        gameDao.clear();
        authDao.clear();
        userDao.clear();
    }
}

