package server;

import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDAO;
import dataaccess.memory.MemoryUserDAO;
import dataaccess.sql.SQLAuthDAO;
import dataaccess.sql.SQLGameDAO;
import dataaccess.sql.SQLUserDAO;
import handler.*;
//import handler.RegisterHandler;
import service.*;
import spark.*;

public class Server {
    private final SQLUserDAO userDao = new SQLUserDAO();
    private final SQLGameDAO gameDao = new SQLGameDAO();
    private final SQLAuthDAO authDao = new SQLAuthDAO();
    private final ClearService clearService = new ClearService(userDao,gameDao,authDao);
    private final RegisterService registerService = new RegisterService(userDao, authDao);
    private final LoginService loginService = new LoginService(userDao,authDao);
    private final LogoutService logoutService = new LogoutService(authDao);
    private final ListGamesService listGamesService = new ListGamesService(authDao, gameDao);
    private final CreateGameService createGameService = new CreateGameService(authDao, gameDao);
    private final JoinGameService joinGameService = new JoinGameService(authDao,gameDao);


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        ClearHandler clearHandler = new ClearHandler(clearService);
        RegisterHandler registerHandler = new RegisterHandler(registerService);
        LoginHandler loginHandler = new LoginHandler(loginService);
        LogoutHandler logoutHandler = new LogoutHandler(logoutService);
        ListGameHandler listGameHandler = new ListGameHandler(listGamesService);
        CreateGameHandler createGameHandler = new CreateGameHandler(createGameService);
        JoinGameHandler joinGameHandler = new JoinGameHandler(joinGameService);
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", clearHandler::clearAllData);
        Spark.post("/user", registerHandler::registerUser);
        Spark.post("/session", loginHandler::loginUser);
        Spark.delete("/session", logoutHandler::logoutUser);
        Spark.get("/game", listGameHandler::list);
        Spark.post("/game", createGameHandler::createGame);
        Spark.put("/game", joinGameHandler::joinGame);
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
