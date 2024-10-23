package server;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import handler.*;
//import handler.RegisterHandler;
import service.*;
import spark.*;

public class Server {
    private final UserDAO userDao = new MemoryUserDAO();
    private final GameDAO gameDao = new MemoryGameDAO();
    private final AuthDAO authDao = new MemoryAuthDao();
    private final ClearService clearService = new ClearService(userDao,gameDao,authDao);
    private final RegisterService registerService = new RegisterService(userDao, authDao);
    private final LoginService loginService = new LoginService(userDao,authDao);
    private final LogoutService logoutService = new LogoutService(authDao);
    private final ListGamesService listGamesService = new ListGamesService(authDao, gameDao);
    private final CreateGameService createGameService = new CreateGameService(authDao, gameDao);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        ClearHandler clearHandler = new ClearHandler(clearService);
        RegisterHandler registerHandler = new RegisterHandler(registerService);
        LoginHandler loginHandler = new LoginHandler(loginService);
        LogoutHandler logoutHandler = new LogoutHandler(logoutService);
        ListGameHandler listGameHandler = new ListGameHandler(listGamesService);
        CreateGameHandler createGameHandler = new CreateGameHandler(createGameService);
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", clearHandler::clearAllData);
        Spark.post("/user", registerHandler::registerUser);
        Spark.post("/session", loginHandler::loginUser);
        Spark.delete("/session", logoutHandler::logoutUser);
        Spark.get("/game", listGameHandler::list);
        Spark.post("/game", createGameHandler::createGame);
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
