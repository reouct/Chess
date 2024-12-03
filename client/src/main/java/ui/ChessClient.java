package ui;

import chess.ChessBoard;
import chess.ChessGame;
import model.GameData;
import request.*;
import result.AuthResult;
import result.ListGameResult;
import result.GameResult;
import result.Result;
import server.ServerFacade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ChessClient {
    private final ServerFacade serverFacade;
    private final Scanner scanner;
    private Map<Integer,Integer> gameList;
    private final Repl repl;

    public ChessClient(int port) {
        serverFacade = new ServerFacade(port);
        scanner = new Scanner(System.in);
        repl = new Repl(port);
    }


    public void run() throws IOException {
        while (true) {
            if (serverFacade.getAuthToken() == null) {
                preLogin();
            } else {
                postLogin();
            }
        }
    }

    private void preLogin() {
        System.out.println("\nWelcome to 240 chess. Type Help to get started. Type Quit to quit. ");

        boolean isVaild = false;
        while (!isVaild) {
            System.out.println("\n Your command: ");
            String cmd = scanner.nextLine().toLowerCase().trim();
            isVaild = true;
            switch (cmd) {
                case "quit" -> System.exit(0);
                case "help" -> preLoginHelp();
                default -> {
                    isVaild = false;
                    System.out.println("Wrong command");
                }
            }
        }


    }

    private void preLoginHelp() {
        System.out.println("\nHere are some commands you can use.");
        boolean isVaild = false;
        while (!isVaild) {
            System.out.println("\n Type Register to create an account.");
            System.out.println(("\n Type Login to login in your account."));
            System.out.println("\n Type Quit to quit the program.");
            System.out.println("\n Type Help for some possible command. ");
            System.out.println("\n Your command: ");
            String cmd = scanner.nextLine().toLowerCase().trim();
            isVaild = true;
            switch (cmd) {
                case "login" -> login();
                case "register" -> register();
                case "quit" -> System.exit(0);
                case "help" -> preLoginHelp();
                default -> {
                    isVaild = false;
                    System.out.println("Wrong command");
                }
            }
        }
    }


    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.next();
        scanner.nextLine();

        // Login this user in
        LoginRequest request = new LoginRequest(username, password);
        AuthResult result = serverFacade.login(request);

        // Set username and authToken
        if(result.message() != null) {
            System.out.println("\n" + result.message());
        }
        else {
            serverFacade.setAuthToken(result.authToken());
            serverFacade.setUsername(result.username());
            System.out.println("You are logged in as: " + serverFacade.getUsername());
        }

    }

    private void register() {
        System.out.print("Enter your email: ");
        String email = scanner.next();
        scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.next();
        scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.next();
        scanner.nextLine();

        RegisterRequest registerRequest = new RegisterRequest(username,password,email);
        AuthResult authResult = serverFacade.register(registerRequest);

        if(authResult.message() != null) {
            System.out.println("\n" + authResult.message());
        }
        else {
            serverFacade.setAuthToken(authResult.authToken());
            serverFacade.setUsername(authResult.username());
            System.out.println("\nAccount created, you are now logged in as: " + serverFacade.getUsername());
        }
    }


    private void postLogin() throws IOException {
        System.out.print("Enter one of the following commands to continue.");
        System.out.print("\n Type Create Game to create a game");
        System.out.print("\n Type List Game to list a game");
        System.out.print("\n Type Join Game to join a game");
        System.out.print("\n Type Join Observer to join as an observer");
        System.out.print("\n Type Logout to logout");
        System.out.print("\n Type Help for some possible command");

        boolean valid = false;
        while (!valid) {
            System.out.print("\nCommand: ");
            String cmd = scanner.nextLine().toLowerCase().trim();
            valid = true;
            switch (cmd) {
                case "list game" -> listGames();
                case "create game" -> createGame();
                case "join game" -> joinGame();
                case "join observer" -> joinObserver();
                case "logout" -> logout();
                case "help" -> {
                    System.out.println("Type one of the commands then hit Enter.");
                    postLoginHelp();
                }
                default -> {
                    valid = false;
                    System.out.println("Unrecognized command, try again.");
                }
            }
        }
    }

    private void postLoginHelp() throws IOException {
        boolean isVaild = false;
        while (!isVaild) {
            System.out.print("Enter one of the following commands to continue.");
            System.out.print("\n Type Create Game to create a game");
            System.out.print("\n Type List Game to list a game");
            System.out.print("\n Type Join Game to join a game");
            System.out.print("\n Type Join Observer to join as an observer");
            System.out.print("\n Type Logout to logout");
            System.out.print("\n Type Help for some possible command");
            System.out.println("\n Your command: ");
            String cmd = scanner.nextLine().toLowerCase().trim();
            isVaild = true;
            switch (cmd) {
                case "create game" -> createGame();
                case "list game" -> listGames();
                case "join game" -> joinGame();
                case "join observer" -> joinObserver();
                case "logout" -> logout();
                case "help" -> preLoginHelp();
                default -> {
                    isVaild = false;
                    System.out.println("Wrong command");
                }
            }
        }
    }

    private void logout() {
        LogoutRequest logoutRequest = new LogoutRequest(serverFacade.getAuthToken());
        AuthResult result = serverFacade.logout(logoutRequest);

        if(result.message() != null) {
            System.out.println("\n" + result.message());
        }
        else {
            serverFacade.setAuthToken(null);
            serverFacade.setUsername(null);
            System.out.println("You are successfully logged out!");
        }
    }

    private void joinObserver() throws IOException {
        if (gameList != null) {
            System.out.print("Enter game number: ");
            int num = scanner.nextInt();
            scanner.nextLine();

            ChessGame.TeamColor view = ChessGame.TeamColor.WHITE;

            JoinGameRequest request = new JoinGameRequest("Observer", gameList.get(num), serverFacade.getAuthToken());
            Result result = serverFacade.joinGame(request);

            if (result.message() != null) {
                System.out.println("\n" + result.message());
            } else {
                System.out.println("You just joined as an observer!");
                repl.run(serverFacade.getAuthToken(), request.gameID(), view,true);
            }
        } else {
            System.out.println("List what games are available first!");
        }
    }

    private void joinGame() throws IOException {
        if (gameList != null) {
            System.out.print("Enter game number: ");
            int num = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter player color: ");
            String playerColor = scanner.next().toUpperCase();
            scanner.nextLine();

            if (playerColor.equals("WHITE") || playerColor.equals("BLACK")) {
                JoinGameRequest request = new JoinGameRequest(playerColor, gameList.get(num), serverFacade.getAuthToken());
                Result result = serverFacade.joinGame(request);

                if (result != null) {
                    System.out.println("\n" + result.message());
                } else {
                    ChessGame.TeamColor view = playerColor.equals("WHITE") ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
                    repl.run(serverFacade.getAuthToken(), request.gameID(), view,false);
                }
            } else {
                System.out.println("Wrong color. Try again");
            }
        } else {
            System.out.println("List what games are available first!");
        }
    }

    private void createGame() {
        System.out.print("Enter a game name: ");
        String gameName = scanner.nextLine();

        CreateGameRequest createGameRequest = new CreateGameRequest(gameName, serverFacade.getAuthToken());
        GameResult result = serverFacade.createGame(createGameRequest);

        if(result != null) {
            System.out.println("\n" + result.message());
        }
        else {
            System.out.println("You successfully created a game with game name: " + gameName );
        }

    }

    private void listGames() {
        System.out.println("Here are the list of the games");
        ListGameRequest listGameRequest = new ListGameRequest(serverFacade.getAuthToken());
        ListGameResult result = serverFacade.listGames(listGameRequest);
        Set<GameData> gameDataSet = result.games();

        if (result.message() != null) {
            System.out.println("\n" + result.message());
        }

        if (gameDataSet == null) {
            System.out.println("There are no games going on! Create one to play!");
        }

        System.out.println("\t#\tName\tWhite\tBlack");

        gameList = new HashMap<>();
        int count = 0;

        assert gameDataSet != null;
        for (GameData game : gameDataSet) {
            ++count;
            gameList.put(count, game.gameID());

            String whiteUsername = (game.whiteUsername() != null) ? game.whiteUsername() : "-----";
            String blackUsername = (game.blackUsername() != null) ? game.blackUsername() : "-----";

            System.out.println("\t" + count + "\t" + game.gameName() + "\t" + whiteUsername + "\t" + blackUsername);
        }
    }

}
