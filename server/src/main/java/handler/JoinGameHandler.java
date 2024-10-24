package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.ErrorMessages;
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

        JoinData joinData = new Gson().fromJson(req.body(), JoinData.class);

        ErrorMessages attempt1 = new ErrorMessages("Error: unauthorized");
        ErrorMessages attempt2 = new ErrorMessages("Error: already taken");
        ErrorMessages attempt3 = new ErrorMessages("Error: bad request");
        ErrorMessages attempt4 = new ErrorMessages("Error");

        try {
            joinGameService.joinGame(authToken,new JoinData(joinData.playerColor(), joinData.gameID()));
            res.status(200);
            return "";
        } catch ( DataAccessException e) {
            String errorMessage = e.getMessage();
            switch (errorMessage) {
                case "Error: unauthorized" -> {
                    res.status(401);
                    return new Gson().toJson(attempt1);
                }
                case "Error: already taken" -> {
                    res.status(403);
                    return new Gson().toJson(attempt2);
                }
                case "Error: bad request" -> {
                    res.status(400);
                    return new Gson().toJson(attempt3);
                }
            }
        }
        return new Gson().toJson(attempt4);
    }
}
