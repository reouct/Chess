package ui;

import chess.ChessGame;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Scanner;

public class Repl implements NotificationHandler {
    static Scanner scanner = new Scanner(System.in);

    private static WebSocketFacade webSocketFacade;

    private int port;
    private static String authToken;
    private static int gameID;
    private ChessGame.TeamColor view;

    public Repl(int port) {
        this.port = port;
        webSocketFacade = new WebSocketFacade(8080, this);
    }


    public static void run(String authToken, int gameID, ChessGame.TeamColor view, boolean isObserving) throws IOException {
        Repl.authToken = authToken;
        Repl.gameID = gameID;

        chess.ChessBoard chessBoard = new chess.ChessBoard();
        chessBoard.resetBoard();

        if (isObserving) {
            ChessBoard.printChessBoard(chessBoard, ChessGame.TeamColor.WHITE);
        } else if (view == ChessGame.TeamColor.WHITE){
            ChessBoard.printChessBoard(chessBoard, ChessGame.TeamColor.WHITE);
        } else if (view == ChessGame.TeamColor.BLACK) {
            ChessBoard.printChessBoard(chessBoard, ChessGame.TeamColor.BLACK);
        }

        help();

        String next = "";
        while (!next.equals("leave")) {
            next = scanner.next().toLowerCase();
            scanner.nextLine();

            switch(next) {
                case "make" -> makeMove();
                case "highlight" -> highlightLegalMoves();
                case "redraw" -> redrawChessBoard();
                case "resign" -> resign();
                case "leave" -> leave();
                case "help" -> help();
                default -> System.out.println("Invalid command, type 'help' for options.");
            }
        }
        System.out.println();
    }

    private static void help() {
        System.out.println("\nType one of the following commands and hit enter.");
        System.out.println("\tmake move");
        System.out.println("\thighlight legal moves");
        System.out.println("\tredraw chess board");
        System.out.println("\tresign");
        System.out.println("\tleave");
        System.out.println("\thelp");
    }

    private static void leave() throws IOException {
        webSocketFacade.leave(authToken, gameID);
    }

    private static void resign() {
        System.out.println("not implemented");
    }

    private static void redrawChessBoard() {
        System.out.println("not implemented");
    }

    private static void highlightLegalMoves() {
        System.out.println("not implemented");
    }

    private static void makeMove() {
        System.out.println("not implemented");
    }

    @Override
    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()) {
            case NOTIFICATION -> {
                NotificationMessage notificationMessage = (NotificationMessage) notification;
                System.out.println(notificationMessage.getMessage());
            }
            case LOAD_GAME -> {
                LoadGameMessage loadGameMessage = (LoadGameMessage) notification;
                ChessGame game = loadGameMessage.getGame();
                System.out.println(" ");
                ChessBoard.printChessBoard(game.getBoard(), view);
            }
            case ERROR -> {
                ErrorMessage errorMessage = (ErrorMessage) notification;
                System.out.println("Error: "+errorMessage.getErrorMessage());
            }
        }
    }
}
