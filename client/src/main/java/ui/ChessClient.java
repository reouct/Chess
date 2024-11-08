package ui;

import server.ServerFacade;

import java.util.Scanner;

public class ChessClient {
    private ServerFacade serverFacade;
    private Scanner sc;

    public ChessClient(int port) {
        serverFacade = new ServerFacade(port);
        sc = new Scanner(System.in);

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
        System.out.println("\nWelcome to 240 chess. Type Help to get started.");
        String cmd = sc.nextLine().toLowerCase().trim();
        switch (cmd) {
            case "login" -> login();
            case "register" -> register();
            case "quit" -> System.exit(0);
            case "help" -> System.out.println("help");
            default -> System.out.println("Wrong command");
        }


    }

    private void login() {
        // not implemented
    }

    private void register() {
        // not implemented
    }


    private void postLogin() {
    }

}
