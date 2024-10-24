package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.ErrorMessages;
import model.GameData;
import service.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.Map;

public class ListGameHandler {
    private final ListGamesService listGamesService;

    public ListGameHandler(ListGamesService listGamesService) {
        this.listGamesService = listGamesService;
    }


    public Object list(Request req, Response res) throws DataAccessException {
        String authtoken = req.headers("authorization");
        ErrorMessages attempt = new ErrorMessages("Error: unauthorized");

        try {
            Collection<GameData> gameData = listGamesService.listGame(authtoken);
            res.status(200);
            res.type("application/json");
            return new Gson().toJson(Map.of("games", gameData));
        } catch (DataAccessException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Error: unauthorized")) {
                res.status(401);
                return new Gson().toJson(attempt);
            } else {
                res.status(500);
            }
        }
        return "Error";
    }
}
