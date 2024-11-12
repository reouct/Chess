package client;

import request.*;
import org.junit.jupiter.api.*;
import result.AuthResult;
import result.ListGameResult;
import result.GameResult;
import result.Result;
import server.Server;
import server.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    private final static RegisterRequest registerRequest = new RegisterRequest("testUser", "testPassword", "testemail");
    private final static LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade(port);
    }

    @BeforeEach
    public void clean() {
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        serverFacade.clear();
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void clearTest() {
        serverFacade.clear();

        Result result = serverFacade.clear();

        Assertions.assertNull(result.message());
    }

    @Test
    public void registerTestPositive() {
        AuthResult authResult = serverFacade.register(registerRequest);
        Assertions.assertNull(authResult.message());
    }

    @Test
    public void registerTestNegative() {
        AuthResult authResult = serverFacade.register(new RegisterRequest(null, null, null));
        Assertions.assertNotNull(authResult.message());
    }

    @Test
    public void loginTestPositive() {
        serverFacade.register(registerRequest);

        AuthResult authResult = serverFacade.login(loginRequest);
        Assertions.assertNull(authResult.message());
    }

    @Test
    public void loginTestNegative() {
        AuthResult authResult = serverFacade.login(loginRequest);
        Assertions.assertNotNull(authResult.message());
    }

    @Test
    public void logoutTestPositive() {
        AuthResult authResult = serverFacade.register(registerRequest);
        String authToken = authResult.authToken();

        LogoutRequest logoutRequest = new LogoutRequest(authToken);
        AuthResult result = serverFacade.logout(logoutRequest);
        Assertions.assertNull(result.message());
    }

    @Test
    public void logoutTestNegative() {
        serverFacade.register(registerRequest);

        LogoutRequest logoutRequest = new LogoutRequest("notAuthToken");
        AuthResult result = serverFacade.logout(logoutRequest);
        Assertions.assertNotNull(result.message());
    }

    @Test
    public void createGameTestPositive() {
        AuthResult authResult = serverFacade.register(registerRequest);
        String authToken = authResult.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("name", authToken);
        GameResult gameResult = serverFacade.createGame(createGameRequest);
        Assertions.assertNull(gameResult.message());
    }

    @Test
    public void createGameTestNegative() {
        serverFacade.register(registerRequest);

        CreateGameRequest createGameRequest = new CreateGameRequest("name", "notAuthToken");
        GameResult gameResult = serverFacade.createGame(createGameRequest);
        Assertions.assertNotNull(gameResult.message());
    }

    @Test
    public void listGamesTestPositive() {
        AuthResult authResult = serverFacade.register(registerRequest);
        String authToken = authResult.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("name", authToken);
        serverFacade.createGame(createGameRequest);
        ListGameRequest listGamesRequest = new ListGameRequest(authToken);
        ListGameResult listGameResult = serverFacade.listGames(listGamesRequest);
        Assertions.assertNull(listGameResult.message());
    }

    @Test
    public void listGamesTestNegative() {
        AuthResult authResult = serverFacade.register(registerRequest);
        String authToken = authResult.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("name", authToken);
        serverFacade.createGame(createGameRequest);
        ListGameRequest listGamesRequest = new ListGameRequest("notAuthToken");
        ListGameResult listGameResult = serverFacade.listGames(listGamesRequest);
        Assertions.assertNotNull(listGameResult.message());
    }

    @Test
    public void joinGameTestPositive() {
        AuthResult authResult = serverFacade.register(registerRequest);
        String authToken = authResult.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("name", authToken);
        GameResult gameResult = serverFacade.createGame(createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameResult.gameID(), authToken);
        Result result = serverFacade.joinGame(joinGameRequest);
        Assertions.assertNull(result);
    }

    @Test
    public void joinGameTestNegative() {
        AuthResult authResult = serverFacade.register(registerRequest);
        String authToken = authResult.authToken();

        CreateGameRequest createGameRequest = new CreateGameRequest("name", authToken);
        GameResult gameResult = serverFacade.createGame(createGameRequest);
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", gameResult.gameID(), "notAuthToken");
        Result result = serverFacade.joinGame(joinGameRequest);
        Assertions.assertNotNull(result.message());
    }

}
