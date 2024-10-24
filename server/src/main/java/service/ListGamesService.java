package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.Objects;

public class ListGamesService {

    private final AuthDAO authDao;
    private final GameDAO gameDao;


    public ListGamesService(AuthDAO authDao, GameDAO gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public Collection<GameData> listGame(String authToken) throws DataAccessException {
            AuthData authData = authDao.getAuth(authToken);
            if (authData == null || !Objects.equals(authData.authToken(), authToken)) {
                throw new DataAccessException("Error: unauthorized");
        }

            return gameDao.listGames();
    }
}
