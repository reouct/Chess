package ui;

import chess.ChessGame;
import ui.ChessBoard;

public class Repl {

    public static void run(ChessGame.TeamColor view, boolean isObserving) {
        chess.ChessBoard chessBoard = new chess.ChessBoard();
        chessBoard.resetBoard();

        if (isObserving) {
            ChessBoard.printChessBoard(chessBoard, ChessGame.TeamColor.WHITE);
        } else {
            ChessBoard.printChessBoard(chessBoard,view);
        }
    }
}
