package handler;

import service.RegisterService;
import spark.Request;
import spark.Response;

import javax.xml.transform.Result;

public class RegisterHandler {
    static RegisterService registerService;

    public RegisterHandler(RegisterService registerService) {
        this.registerService = registerService;
    }

    public static Object registerUser(Request req, Response res){

        try {
            registerService.register();
            res.status(200);
            return "{ 'username':'', 'authToken':'' }";
        } catch (Exception e) {
            res.status(500);
            throw new RuntimeException(e);
        }

    }
}
