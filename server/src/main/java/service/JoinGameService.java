package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import model.AuthData;
import model.GameData;
import model.JoinData;

import java.util.Objects;

public class JoinGameService {
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public JoinGameService(AuthDAO authDao, GameDAO gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public void joinGame(String authToken, JoinData join) throws DataAccessException{
        AuthData authData = authDao.getAuth(authToken);
        GameData gameData = gameDao.getGame(join.gameID());

        if (authData == null || !Objects.equals(authData.authToken(), authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }

        if (gameData == null || gameData.gameID() != join.gameID() || join.playerColor() == null) {
            throw new DataAccessException("Error: bad request");
        }

        gameDao.getGame(join.gameID());

        if (join.playerColor().equalsIgnoreCase("White")) {
            if (gameData.whiteUsername() == null) {
                gameDao.updateGame(new GameData(gameData.gameID(),authData.username(), gameData.blackUsername(),gameData.gameName(),gameData.game()));
            } else {
                throw new DataAccessException("Error: already taken");
            }
        } else if (join.playerColor().equalsIgnoreCase("Black")){
            if (gameData.blackUsername() == null) {
                gameDao.updateGame(new GameData(gameData.gameID(),gameData.whiteUsername(),authData.username(),gameData.gameName(),gameData.game()));
            } else {
                throw new DataAccessException("Error: already taken");
            }
        } else if (join.playerColor().equalsIgnoreCase("Observer")){
            System.out.println("You just joined as an observer!");
        }

    }
}
