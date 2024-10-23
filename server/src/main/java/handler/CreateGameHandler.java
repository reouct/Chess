package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.ErrorMessages;
import model.GameData;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    private final CreateGameService createGameService;
    int gameID = 1;

    public CreateGameHandler(CreateGameService createGameService) {
        this.createGameService = createGameService;
    }

    public Object createGame(Request req, Response res) throws DataAccessException {
        String authtoken = req.headers("authorization");
        var serializer = new Gson();
        GameData gameData = serializer.fromJson(req.body(), GameData.class);
        //int gameID = -1;

        String gameName = gameData.gameName();

        gameID = gameID+1;

        ErrorMessages attempt1 = new ErrorMessages("Error: unauthorized");
        ErrorMessages attempt2 = new ErrorMessages("Error: bad request");

        try {
            createGameService.createGame(authtoken, gameID, gameName);
            res.status(200);
            return String.format("{\"gameID\": %s}", gameID);
        } catch (DataAccessException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(attempt1);
            } else if (errorMessage.equals("Error: bad request")) {
                return new Gson().toJson(attempt2);
            } else {
                res.status(500);
            }
        }
        return "Error";
    }
}
