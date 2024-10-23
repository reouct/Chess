package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.Objects;

public class ListGamesService {
    //private final UserDAO userDao;
    private final AuthDAO authDao;
    private final GameDAO gameDao;


    public ListGamesService(AuthDAO authDao, GameDAO gameDao) {
        //this.userDao = userDao;
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

//    public void getAuth(String authData) throws DataAccessException {
//        AuthData authdata = authDao.getAuth(authData);
//
//        if (authData == null || authdata == null || authdata.authToken() == null || !Objects.equals(authdata.authToken(), authData)){
//            throw new DataAccessException("Error: unauthorized");
//        }
//
//        authDao.getAuth(authData);
//    }
//
//    public GameData getAllGames() throws DataAccessException {
//        GameData gameData = new GameData(gameDao.listGames());
//        return gameData;
//    }

    public Collection<GameData> listGame(String authToken) throws DataAccessException {
            AuthData authData = authDao.getAuth(authToken);
            if (authData == null || !Objects.equals(authData.authToken(), authToken)) {
                throw new DataAccessException("Error: unauthorized");
        }

            return gameDao.listGames();
    }
}
