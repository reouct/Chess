package service;

import chess.ChessGame;
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

        if (gameData.gameID() != join.gameID()) {
            throw new DataAccessException("Error: bad request");
        }

        gameDao.getGame(join.gameID());

        GameData newGame;
        if (join.desiredColor().equalsIgnoreCase("White")) {
            if (gameData.whiteUsername() == null) {
                newGame = new GameData(gameData.gameID(),authData.username(), gameData.blackUsername(),gameData.gameName(),gameData.game());
            } else {
                throw new DataAccessException("already taken");
            }
        } else {
            if (gameData.blackUsername() == null) {
                newGame = new GameData(gameData.gameID(), gameData.blackUsername(), authData.username(), gameData.gameName(),gameData.game());
            } else {
                throw new DataAccessException("already taken");
            }

        }

        gameDao.updateGame(newGame);

    }
}
