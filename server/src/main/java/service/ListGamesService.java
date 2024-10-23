package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import model.GameData;

public class ListGamesService {
    //private final UserDAO userDao;
    //private final AuthDAO authDao;
    private final GameDAO gameDao;


    public ListGamesService(GameDAO gameDao) {
        //this.userDao = userDao;
        //this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public String listGame(GameData data) throws DataAccessException {
        GameData gameData = gameDao.getGame(data);

        if (gameData == null) {
            throw new DataAccessException("Error: unauthorized");
        }

        String gamelist = String.valueOf(gameDao.getGame(data));
        return gamelist;
    }
}
