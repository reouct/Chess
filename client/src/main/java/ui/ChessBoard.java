package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class ChessBoard {
    private static final String LABEL = SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + SET_TEXT_FAINT;
    private static final String END_LABEL = SET_BG_COLOR_DARK_GREY + SET_TEXT_COLOR_WHITE + SET_TEXT_FAINT;
    private static final String WHITE = SET_BG_COLOR_WHITE + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD;
    private static final String BLACK = SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + SET_TEXT_BOLD;

    private static final String[][] STYLES = {
            {LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL},
            {LABEL, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, LABEL},
            {LABEL, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, LABEL},
            {LABEL, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, LABEL},
            {LABEL, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, LABEL},
            {LABEL, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, LABEL},
            {LABEL, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, LABEL},
            {LABEL, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, LABEL},
            {LABEL, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, BLACK, WHITE, LABEL},
            {LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL, LABEL}
    };

    private static final String[][] TEXT = new String[10][10];

    private static String[] toStringArray(String str) {
        String[] arr = new String[str.length()];
        for(int i = 0; i < str.length(); ++i) {
            arr[i] = str.substring(i,i+1);
        }
        return arr;
    }

    private static String toCharacterRepresentation(ChessPiece piece) {
        if(piece == null) {
            return " ";
        }
        return switch (piece.getPieceType()) {
            case KING -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                    ? SET_TEXT_COLOR_RED + "K" : SET_TEXT_COLOR_BLUE + "k";
            case QUEEN -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                    ? SET_TEXT_COLOR_RED + "Q" : SET_TEXT_COLOR_BLUE + "q";
            case ROOK -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                    ? SET_TEXT_COLOR_RED + "R" : SET_TEXT_COLOR_BLUE + "r";
            case BISHOP -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                    ? SET_TEXT_COLOR_RED + "B" : SET_TEXT_COLOR_BLUE + "b";
            case KNIGHT -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                    ? SET_TEXT_COLOR_RED + "N" : SET_TEXT_COLOR_BLUE + "n";
            case PAWN -> (piece.getTeamColor() == ChessGame.TeamColor.WHITE)
                    ? SET_TEXT_COLOR_RED + "P" : SET_TEXT_COLOR_BLUE + "p";
        };
    }

    public static void printChessBoard(chess.ChessBoard board, ChessGame.TeamColor view){
        String[] rowLabels;
        String[] colLabels;


        if (view == ChessGame.TeamColor.WHITE) {
            rowLabels = toStringArray(" abcdefgh ");
            colLabels = toStringArray(" 87654321 ");
        } else {
            rowLabels = toStringArray(" hgfedcba ");
            colLabels = toStringArray(" 12345678 ");
        }

        // Labels
        TEXT[0] = rowLabels;
        TEXT[9] = rowLabels;
        for(int i = 0; i < 10; ++i){
            TEXT[i][0] = colLabels[i];
            TEXT[i][9] = colLabels[i];
        }

        

        // Chess pieces
        for (int r = 1; r < 9; ++r) {
            for (int c = 1; c < 9; ++c) {
                if(view == ChessGame.TeamColor.WHITE) {
                    ChessPiece piece = board.getPiece(new ChessPosition(r, c));
                    TEXT[r][c] = toCharacterRepresentation(piece);
                }
                else {
                    ChessPiece piece = board.getPiece(new ChessPosition(r, c));
                    TEXT[r][c] = toCharacterRepresentation(piece);
                }
            }
        }

        for (int r = 0; r < 10; ++r) {
            for (int c = 0; c < 10; ++c) {
                System.out.print(STYLES[r][c]);
                System.out.print(" " + TEXT[r][c] + " ");
                if (c == 9) {
                    System.out.print(END_LABEL);
                }
            }
            System.out.println();
        }
    }
}
