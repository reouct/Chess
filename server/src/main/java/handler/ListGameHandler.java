package handler;

import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    public ListGameHandler(ListGamesService listGamesService) {
    }

    public Object list(Request req, Response res) {
        return "";
    }
}
