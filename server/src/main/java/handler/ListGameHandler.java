package handler;

import com.google.gson.Gson;
import model.GameData;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    private final ListGamesService listGamesService;

    public ListGameHandler(ListGamesService listGamesService) {
        this.listGamesService = listGamesService;
    }


    public Object list(Request req, Response res) {
        String data = req.headers("authorization");
        res.status(200);
        GameData gameData = listGamesService.listGame(data)
                
    }
}
