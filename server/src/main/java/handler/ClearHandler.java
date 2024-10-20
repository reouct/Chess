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
    public static Object clearAllData(Request req, Response res) throws DataAccessException {
        clearService.clear();
        res.status(200);
        return "{}";
    }

//    public static Object clearApplication(Request request, Response response) {
//        String responseBody = "";
//        try {
//            ClearService.clear();
//            response.status(200);
//            responseBody = "{\"message\": \"Application data cleared successfully\"}";
//        } catch (DataAccessException e) {
//            response.status(e.statusCode);
//            responseBody = "{\"message\": \"" + e.getMessage() + "\"}";
//        } finally {
//            response.type("application/json");
//            return responseBody;
//        }
//    }
}
