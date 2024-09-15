package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static chess.ChessPiece.PieceType.QUEEN;
import static chess.ChessPiece.PieceType.BISHOP;
import static chess.ChessPiece.PieceType.KNIGHT;
import static chess.ChessPiece.PieceType.ROOK;

public class addMoves {

    public static void addMove(Collection<ChessMove> moves, int startRow, int startCol, int endRow, int endCol, boolean isPromoting) {
        if (isPromoting) {
            moves.add(new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow, endCol), QUEEN));
            moves.add(new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow, endCol), KNIGHT));
            moves.add(new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow, endCol), ROOK));
            moves.add(new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow, endCol), BISHOP));
        } else {
            moves.add(new ChessMove(new ChessPosition(startRow, startCol), new ChessPosition(endRow, endCol), null));
        }
    }

    public static boolean addValidMoveCollsions(Collection<ChessMove> moves, ChessBoard board, int current_row, int current_col, int row, int col) {
        ChessPiece current_piece = board.getPiece(new ChessPosition(row, col));
        if (current_piece == null) {
            addMove(moves, current_row, current_col, row, col, false);
        } else {
            if (current_piece.getTeamColor() != board.getPiece(new ChessPosition(current_row, current_col)).getTeamColor()) {
                addMove(moves, current_row, current_col, row, col, false);
            }
            return true;
        }
        return false;
    }

    public static ArrayList<ChessMove> addBishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();

        // Move up and right
        for (int row = my_currentrow + 1, col = my_currentcol + 1; row <= 8 && col <= 8; ++row, ++col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
            ;
        }

        // Move down and right
        for (int row = my_currentrow - 1, col = my_currentcol + 1; row >= 1 && col <= 8; --row, ++col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
            ;
        }

        // Move down and left
        for (int row = my_currentrow - 1, col = my_currentcol - 1; row >= 1 && col >= 1; --row, --col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
        }

        // Move Up and left
        for (int row = my_currentrow + 1, col = my_currentcol - 1; row <= 8 && col >= 1; ++row, --col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
        }

        return moves;

    }

    public static ArrayList<ChessMove> addQueenMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();

        // Move up and right
        for (int row = my_currentrow + 1, col = my_currentcol + 1; row <= 8 && col <= 8; ++row, ++col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
            ;
        }

        // Move down and right
        for (int row = my_currentrow - 1, col = my_currentcol + 1; row >= 1 && col <= 8; --row, ++col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
            ;
        }

        // Move down and left
        for (int row = my_currentrow - 1, col = my_currentcol - 1; row >= 1 && col >= 1; --row, --col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
        }

        // Move Up and left
        for (int row = my_currentrow + 1, col = my_currentcol - 1; row <= 8 && col >= 1; ++row, --col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, col)) {
                break;
            }
        }

        // Move Up
        for (int row = my_currentrow + 1; row <= 8; ++row) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, my_currentcol)) {
                break;
            }
        }

        // Move left
        for (int col = my_currentcol - 1; col >= 1; --col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, my_currentrow, col)) {
                break;
            }
        }

        // Move down
        for (int row = my_currentrow - 1; row >= 1; --row) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, my_currentcol)) {
                break;
            }
        }

        // Move right
        for (int col = my_currentcol + 1; col <= 8; ++col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, my_currentrow, col)) {
                break;
            }
        }


        return moves;
    }

    public static ArrayList<ChessMove> addRookMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();

        // Move Up
        for (int row = my_currentrow + 1; row <= 8; ++row) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, my_currentcol)) {
                break;
            }
        }

        // Move left
        for (int col = my_currentcol - 1; col >= 1; --col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, my_currentrow, col)) {
                break;
            }
        }

        // Move down
        for (int row = my_currentrow - 1; row >= 1; --row) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, row, my_currentcol)) {
                break;
            }
        }

        // Move right
        for (int col = my_currentcol + 1; col <= 8; ++col) {
            if (addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, my_currentrow, col)) {
                break;
            }
        }

        return moves;
    }

    public static ArrayList<ChessMove> addKingMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();


        // King can only move to the square around him
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = my_currentrow + rowOffsets[i];
            int newCol = my_currentcol + colOffsets[i];
            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, newRow, newCol);
            }
        }
        return moves;
    }

    public static ArrayList<ChessMove> addKnightMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();

        // Knight moves L shape
        int[] rowOffsets = {-1, -2, -1, -2, 1, 2, 2, 1};
        int[] colOffsets = {2, 1, -2, -1, 2, 1, -1, -2};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = my_currentrow + rowOffsets[i];
            int newCol = my_currentcol + colOffsets[i];
            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, newRow, newCol);
            }
        }

        return moves;
    }

    public static ArrayList<ChessMove> addWhitePawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();

        // White Pawn can move forward two steps if it's the initial position
        if (my_currentrow == 2 && board.getPiece(new ChessPosition(my_currentrow + 1, my_currentcol)) == null &&
                board.getPiece(new ChessPosition(my_currentrow + 2, my_currentcol)) == null) {
            addMove(moves, my_currentrow, my_currentcol, my_currentrow + 2, my_currentcol, false);
        }

        // White Pawn can move forward one step
        if (my_currentrow < 8 && board.getPiece(new ChessPosition(my_currentrow + 1, my_currentcol)) == null) {

            if (my_currentrow + 1 == 8) {
                addMove(moves, my_currentrow, my_currentcol, my_currentrow + 1, my_currentcol, true);
            } else {
                addMove(moves, my_currentrow, my_currentcol, my_currentrow + 1, my_currentcol, false);
            }
        }

        // White Pawn can capture left diagonally or right diagonally

        // White Pawn move right up diagonally
        if ((board.getPiece(new ChessPosition(my_currentrow + 1, my_currentcol + 1)) != null)){
            int[] rowOffsets = {1};
            int[] colOffsets = {1};

            for (int i = 0; i < rowOffsets.length; i++) {
                int newRow = my_currentrow + rowOffsets[i];
                int newCol = my_currentcol + colOffsets[i];
                addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, newRow, newCol);
            }

        }

        // White pawn moves left up diagonally
        if ((board.getPiece(new ChessPosition(my_currentrow + 1, my_currentcol - 1)) != null)){
            int[] rowOffsets = {1};
            int[] colOffsets = {-1};

            for (int i = 0; i < rowOffsets.length; i++) {
                int newRow = my_currentrow + rowOffsets[i];
                int newCol = my_currentcol + colOffsets[i];
                addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, newRow, newCol);
            }

        }

        return moves;
    }

    public static ArrayList<ChessMove> addBlackPawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int my_currentrow = myPosition.getRow();
        int my_currentcol = myPosition.getColumn();

        // Black Pawn can move forward two steps if it's the initial position
        if (my_currentrow == 7 && board.getPiece(new ChessPosition(my_currentrow - 1, my_currentcol)) == null &&
                board.getPiece(new ChessPosition(my_currentrow - 2, my_currentcol)) == null) {
            addMove(moves, my_currentrow, my_currentcol, my_currentrow - 2, my_currentcol, false);
        }

        // Black Pawn can move forward one step
        if (my_currentrow > 1 && board.getPiece(new ChessPosition(my_currentrow - 1, my_currentcol)) == null) {
            if (my_currentrow - 1 == 1) {
                addMove(moves, my_currentrow, my_currentcol, my_currentrow - 1, my_currentcol, true);
            } else {
                addMove(moves, my_currentrow, my_currentcol, my_currentrow - 1, my_currentcol, false);
            }
        }

        // Black Pawn can capture left diagonally or right diagonally

        // Black Pawn move left down diagonally
        if ((board.getPiece(new ChessPosition(my_currentrow - 1, my_currentcol - 1)) != null)){
            int[] rowOffsets = {-1};
            int[] colOffsets = {-1};

            for (int i = 0; i < rowOffsets.length; i++) {
                int newRow = my_currentrow + rowOffsets[i];
                int newCol = my_currentcol + colOffsets[i];
                addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, newRow, newCol);
            }

        }

        // Black Pawn move right down diagonally
        if ((board.getPiece(new ChessPosition(my_currentrow - 1, my_currentcol + 1)) != null)){
            int[] rowOffsets = {-1};
            int[] colOffsets = {1};

            for (int i = 0; i < rowOffsets.length; i++) {
                int newRow = my_currentrow + rowOffsets[i];
                int newCol = my_currentcol + colOffsets[i];
                addValidMoveCollsions(moves, board, my_currentrow, my_currentcol, newRow, newCol);
            }

        }

        return moves;
    }
}