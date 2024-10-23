package handler;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.ErrorMessages;
import model.GameData;
import model.JoinData;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler {
    private final JoinGameService joinGameService;

    public JoinGameHandler(JoinGameService joinGameService) {
        this.joinGameService = joinGameService;
    }

    public Object joinGame(Request req, Response res) throws DataAccessException {
        String authToken = req.headers("authorization");

        GameData gameData = new Gson().fromJson(req.body(), GameData.class);

        ErrorMessages attempt1 = new ErrorMessages("Error: unauthorized");
        ErrorMessages attempt2 = new ErrorMessages("Error: already taken");
        ErrorMessages attempt3 = new ErrorMessages("Error: bad request");

        try {
            joinGameService.joinGame(authToken,new JoinData("WHITE", gameData.gameID()));
            res.status(200);
            return "";
        } catch ( DataAccessException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(attempt1);
            } else if (errorMessage.equals("Error: already taken")){
                res.status(403);
                return new Gson().toJson(attempt2);
            } else if (errorMessage.equals("Error: bad request")) {
                res.status(400);
                return new Gson().toJson(attempt3);
            }
        }
        return "Error";
    }
}
