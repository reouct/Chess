package handler;

import dataaccess.DataAccessException;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final ClearService clearService;

    public ClearHandler(ClearService clearService){
        this.clearService = clearService;
    }

    public Object clearAllData(Request req, Response res) throws DataAccessException {
        clearService.clear();
        res.status(200);
        return "{}";
    }
}
