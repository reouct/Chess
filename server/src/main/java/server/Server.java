package server;

import handler.ClearHandler;
import spark.*;

public class Server {
    public ClearHandler clearHandler;


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        //clearHandler = new ClearHandler();
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", ClearHandler::clearAllData);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
