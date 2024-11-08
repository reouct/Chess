package client;

import passoff.model.TestResult;
import request.LoginRequest;
import request.RegisterRequest;
import org.junit.jupiter.api.*;
import result.AuthResult;
import result.Result;
import server.Server;
import server.ServerFacade;

import java.net.HttpURLConnection;
import java.util.Locale;


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
        serverFacade.clear();

        Result result = serverFacade.clear();

        Assertions.assertTrue(result.isSuccess());


    }
}
