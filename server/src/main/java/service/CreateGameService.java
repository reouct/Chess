package service;

import chess.ChessBoard;
import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameService {
    private final AuthDAO authDao;
    private final GameDAO gameDao;

    public CreateGameService(AuthDAO authDao, GameDAO gameDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
    }

    public int getLatestGameID() throws DataAccessException {
        return gameDao.getLatestGameID();
    }


    public void createGame(String authToken, Integer gameID, String gameName) throws DataAccessException {
        AuthData authData = authDao.getAuth(authToken);
        if (authData == null || !Objects.equals(authData.authToken(), authToken)) {
            throw new DataAccessException("Error: unauthorized");
        }

        ChessGame newGame = new ChessGame();
        ChessBoard startBoard = new ChessBoard();
        startBoard.resetBoard();
        newGame.setBoard(startBoard);

        GameData data = new GameData(gameID,null, null, gameName, newGame);
        gameDao.createGame(data);
    }
}
