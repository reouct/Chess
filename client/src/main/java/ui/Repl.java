package ui;

import chess.ChessBoard;
import chess.ChessGame;

public class Repl {

    public static void run() {
        ChessGame game = new ChessGame();
        System.out.println(EscapeSequences.ERASE_SCREEN);
        System.out.println(game.getBoard());
    }
}
