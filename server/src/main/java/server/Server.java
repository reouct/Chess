package server;

import handler.ClearHandler;
//import handler.RegisterHandler;
import spark.*;

public class Server {
    public Server(){

    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        //clearHandler = new ClearHandler();
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", ClearHandler::clearAllData);
        //Spark.post("/user", RegisterHandler::registerUser);
        //This line initializes the server and can be removed once you have a functioning endpoint 
        //park.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
