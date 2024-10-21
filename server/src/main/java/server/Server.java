package server;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import handler.ClearHandler;
//import handler.RegisterHandler;
import service.ClearService;
import service.RegisterService;
import spark.*;

public class Server {
    private final UserDAO userDao = new MemoryUserDAO();
    private final GameDAO gameDao = new MemoryGameDAO();
    private final AuthDAO authDao = new MemoryAuthDao();
    private final ClearService clearService = new ClearService(userDao,gameDao,authDao);
    private final RegisterService registerService = new RegisterService(userDao);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        ClearHandler clearHandler = new ClearHandler(clearService);

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", clearHandler::clearAllData);
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
