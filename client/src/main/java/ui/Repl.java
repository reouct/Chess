package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
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

    private final int port;
    private static String authToken;
    private static int gameID;
    private static ChessGame game;
    private ChessGame.TeamColor view;

    public Repl(int port) {
        this.port = port;
        webSocketFacade = new WebSocketFacade(port, this);
    }


    public void run(String authToken, int gameID, ChessGame.TeamColor view, boolean isObserving) throws IOException {
        Repl.authToken = authToken;
        Repl.gameID = gameID;

        chess.ChessBoard chessBoard = new chess.ChessBoard();
        chessBoard.resetBoard();

        game = new ChessGame();

        if (isObserving) {
            webSocketFacade = new WebSocketFacade(port,this);
            webSocketFacade.joinObserver(authToken, gameID);
        } else if (view == ChessGame.TeamColor.WHITE){
            webSocketFacade = new WebSocketFacade(port,this);
            webSocketFacade.joinPlayer(authToken, gameID, ChessGame.TeamColor.WHITE);
        } else if (view == ChessGame.TeamColor.BLACK) {
            webSocketFacade = new WebSocketFacade(port,this);
            webSocketFacade.joinPlayer(authToken, gameID, ChessGame.TeamColor.BLACK);
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
        System.out.println("Start Position: (for example a1)");
        String s = scanner.next().toLowerCase();
        scanner.nextLine();
        System.out.println("End Position: (for example a2)");
        String e = scanner.next().toLowerCase();
        scanner.nextLine();

        int startRow = Character.getNumericValue(s.charAt(1));
        int startCol = 1+(s.charAt(0)-'a');

        int endRow = Character.getNumericValue(e.charAt(1));
        int endCol = 1+(e.charAt(0)-'a');

        ChessPosition startPosition = new ChessPosition(startRow, startCol);
        ChessPosition endPosition = new ChessPosition(endRow, endCol);

        ChessPiece.PieceType type = null;

        // Promotion
        if(startRow>=1 && startRow<=8 && startCol>=1 && startCol<=8
                && game.getBoard().getPiece(startPosition).getPieceType() == ChessPiece.PieceType.PAWN
                && (endRow == 1 || endRow == 8)) {
            System.out.print("Enter promotion piece type: ");
            String typeString = scanner.next().toLowerCase();
            scanner.nextLine();
            switch (typeString) {
                case "queen" -> type = ChessPiece.PieceType.QUEEN;
                case "rook" -> type = ChessPiece.PieceType.ROOK;
                case "bishop" -> type = ChessPiece.PieceType.BISHOP;
                case "knight" -> type = ChessPiece.PieceType.KNIGHT;
            }
        }

        ChessMove move = new ChessMove(startPosition, endPosition, type);

        webSocketFacade.makeMove(authToken, gameID, move);
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
