package ui;

import request.LoginRequest;
import request.RegisterRequest;
import result.AuthResult;
import server.ServerFacade;

import java.util.Scanner;

public class ChessClient {
    private final ServerFacade serverFacade;
    private final Scanner scanner;

    public ChessClient(int port) {
        serverFacade = new ServerFacade(port);
        scanner = new Scanner(System.in);

    }

    public void run() {
        if (serverFacade.getAuthToken() == null ) {
            preLogin();
        }
        else {
            postLogin();
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
                case "help" -> help();
                default -> {
                    isVaild = false;
                    System.out.println("Wrong command");
                }
            }
        }


    }

    private void help() {
        System.out.println("\nHere are some commands you can use.");
        boolean isVaild = false;
        while (!isVaild) {
            System.out.println("\n Type Register to create an account.");
            System.out.println(("\n Type Login to login in your account."));
            System.out.println("\n Type Quit to quit the program.");
            System.out.println("\n Type help for some possible command. ");
            System.out.println("\n Your command: ");
            String cmd = scanner.nextLine().toLowerCase().trim();
            isVaild = true;
            switch (cmd) {
                case "login" -> login();
                case "register" -> register();
                case "quit" -> System.exit(0);
                case "help" -> help();
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
            System.out.println(" You are logged in as: " + serverFacade.getUsername());
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
            System.out.println("\n Account created, you are now logged in as: " + serverFacade.getUsername());
        }
    }


    private void postLogin() {
        // not implemented
    }

}
