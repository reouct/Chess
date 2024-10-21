package handler;

import dataaccess.DataAccessException;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private static ClearService clearService;

    public ClearHandler(ClearService clearService){
        this.clearService = clearService;
    }

//    private Object deleteAllPets(Request req, Response res) throws ResponseException {
//        service.deleteAllPets();
//        res.status(204);
//        return "";
//    }
    public Object clearAllData(Request req, Response res) throws DataAccessException {
        clearService.clear();
        res.status(200);
        return "{}";
    }
}
