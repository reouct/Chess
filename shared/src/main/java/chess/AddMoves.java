package chess;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessPiece.PieceType.QUEEN;
import static chess.ChessPiece.PieceType.BISHOP;
import static chess.ChessPiece.PieceType.KNIGHT;
import static chess.ChessPiece.PieceType.ROOK;

public class AddMoves {

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

    public static boolean addValidMoveCollsions(Collection<ChessMove> moves, ChessBoard board, int currentRow, int currentCol, int row, int col) {
        ChessPiece currentPiece = board.getPiece(new ChessPosition(row, col));
        if (currentPiece == null) {
            addMove(moves, currentRow, currentCol, row, col, false);
        } else {
            if (currentPiece.getTeamColor() != board.getPiece(new ChessPosition(currentRow, currentCol)).getTeamColor()) {
                addMove(moves, currentRow, currentCol, row, col, false);
            }
            return true;
        }
        return false;
    }

    private static void addDiagonalMoves(Collection<ChessMove> moves, ChessBoard board, int currentRow, int currentCol) {
        // Move up and right
        for (int row = currentRow + 1, col = currentCol + 1; row <= 8 && col <= 8; ++row, ++col) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, row, col)) {
                break;
            }
        }

        // Move down and right
        for (int row = currentRow - 1, col = currentCol + 1; row >= 1 && col <= 8; --row, ++col) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, row, col)) {
                break;
            }
        }

        // Move down and left
        for (int row = currentRow - 1, col = currentCol - 1; row >= 1 && col >= 1; --row, --col) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, row, col)) {
                break;
            }
        }

        // Move up and left
        for (int row = currentRow + 1, col = currentCol - 1; row <= 8 && col >= 1; ++row, --col) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, row, col)) {
                break;
            }
        }
    }

    private static void addStraightMoves(Collection<ChessMove> moves, ChessBoard board, int currentRow, int currentCol) {
        // Move Up
        for (int row = currentRow + 1; row <= 8; ++row) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, row, currentCol)) {
                break;
            }
        }

        // Move left
        for (int col = currentCol - 1; col >= 1; --col) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, currentRow, col)) {
                break;
            }
        }

        // Move down
        for (int row = currentRow - 1; row >= 1; --row) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, row, currentCol)) {
                break;
            }
        }

        // Move right
        for (int col = currentCol + 1; col <= 8; ++col) {
            if (addValidMoveCollsions(moves, board, currentRow, currentCol, currentRow, col)) {
                break;
            }
        }
    }

    public static ArrayList<ChessMove> addBishopMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();
        addDiagonalMoves(moves, board, myCurrentrow, myCurrentcol);
        return moves;

    }

    public static ArrayList<ChessMove> addQueenMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();
        addDiagonalMoves(moves, board, myCurrentrow,myCurrentcol);
        addStraightMoves(moves, board, myCurrentrow, myCurrentcol);
        return moves;
    }

    public static ArrayList<ChessMove> addRookMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();
        addStraightMoves(moves, board, myCurrentrow,myCurrentcol);
        return moves;
    }

    private static void addKingKnightMove(Collection<ChessMove> moves,ChessBoard board,int currentRow,int currentCol,int rowOffset,int colOffset) {
        int newRow = currentRow + rowOffset;
        int newCol = currentCol + colOffset;
        if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
            addValidMoveCollsions(moves, board, currentRow, currentCol, newRow, newCol);
        }
    }

    public static ArrayList<ChessMove> addKingMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();


        // King can only move to the square around him
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            addKingKnightMove(moves, board, myCurrentrow, myCurrentcol, rowOffsets[i], colOffsets[i]);
        }
        return moves;
    }

    public static ArrayList<ChessMove> addKnightMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();

        // Knight moves L shape
        int[] rowOffsets = {-1, -2, -1, -2, 1, 2, 2, 1};
        int[] colOffsets = {2, 1, -2, -1, 2, 1, -1, -2};

        for (int i = 0; i < rowOffsets.length; i++) {
            addKingKnightMove(moves, board, myCurrentrow, myCurrentcol, rowOffsets[i], colOffsets[i]);
        }

        return moves;
    }

    public static ArrayList<ChessMove> addWhitePawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();

        // White Pawn can move forward two steps if it's the initial position
        if (myCurrentrow == 2 && board.getPiece(new ChessPosition(myCurrentrow + 1, myCurrentcol)) == null &&
                board.getPiece(new ChessPosition(myCurrentrow + 2, myCurrentcol)) == null) {
            addMove(moves, myCurrentrow, myCurrentcol, myCurrentrow + 2, myCurrentcol, false);
        }

        // White Pawn can move forward one step
        if (myCurrentrow < 8 && board.getPiece(new ChessPosition(myCurrentrow + 1, myCurrentcol)) == null) {

            if (myCurrentrow + 1 == 8) {
                addMove(moves, myCurrentrow, myCurrentcol, myCurrentrow + 1, myCurrentcol, true);
            } else  {
                addMove(moves, myCurrentrow, myCurrentcol, myCurrentrow + 1, myCurrentcol, false);
            }
        }

        // White Pawn can capture left diagonally or right diagonally

        // White Pawn move right up diagonally
        if (myCurrentrow +1 >=1
                && myCurrentrow +1 <=8
                && myCurrentcol +1 >= 1
                && myCurrentcol +1 <=8
                && (board.getPiece(new ChessPosition(myCurrentrow + 1, myCurrentcol + 1)) != null)){
            int[] rowOffsets = {1};
            int[] colOffsets = {1};

            pawnAddMove(board, moves, myCurrentrow, myCurrentcol, rowOffsets, colOffsets);

        }

        // White pawn moves left up diagonally

        if (myCurrentrow +1 >= 1
                && myCurrentcol -1 <= 8
                && myCurrentcol -1 >= 1
                && myCurrentrow +1 <= 8
                && (board.getPiece(new ChessPosition(myCurrentrow + 1, myCurrentcol - 1)) != null)){
            int[] rowOffsets = {1};
            int[] colOffsets = {-1};

            pawnAddMove(board, moves, myCurrentrow, myCurrentcol, rowOffsets, colOffsets);

        }

        return moves;
    }

    private static void pawnAddMove(ChessBoard board,ArrayList<ChessMove> moves,int myCurrentrow,int myCurrentcol,int[] rowOffsets,int[] colOffsets){
        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = myCurrentrow + rowOffsets[i];
            int newCol = myCurrentcol + colOffsets[i];
            // get promoted if the next pawn move is at the baseline
            if (myCurrentrow == 7 ) {
                addMove(moves, myCurrentrow,myCurrentcol,newRow,newCol,true);
            } else{
                addValidMoveCollsions(moves, board, myCurrentrow, myCurrentcol, newRow, newCol);
            }
        }
    }

    public static ArrayList<ChessMove> addBlackPawnMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int myCurrentrow = myPosition.getRow();
        int myCurrentcol = myPosition.getColumn();

        // Black Pawn can move forward two steps if it's the initial position
        if (myCurrentrow == 7 && board.getPiece(new ChessPosition(myCurrentrow - 1, myCurrentcol)) == null &&
                board.getPiece(new ChessPosition(myCurrentrow - 2, myCurrentcol)) == null) {
            addMove(moves, myCurrentrow, myCurrentcol, myCurrentrow - 2, myCurrentcol, false);
        }

        // Black Pawn can move forward one step
        if (myCurrentrow > 1 && board.getPiece(new ChessPosition(myCurrentrow - 1, myCurrentcol)) == null) {
            if (myCurrentrow - 1 == 1) {
                addMove(moves, myCurrentrow, myCurrentcol, myCurrentrow - 1, myCurrentcol, true);
            } else {
                addMove(moves, myCurrentrow, myCurrentcol, myCurrentrow - 1, myCurrentcol, false);
            }
        }

        // Black Pawn can capture left diagonally or right diagonally

        // Black Pawn move left down diagonally
        if (myCurrentrow -1 >=1
                && myCurrentrow -1 <=8
                && myCurrentcol -1 >= 1
                && myCurrentcol -1 <=8
                &&(board.getPiece(new ChessPosition(myCurrentrow - 1, myCurrentcol - 1)) != null)){
            int[] rowOffsets = {-1};
            int[] colOffsets = {-1};

            pawnAddPromotion(board, moves, myCurrentrow, myCurrentcol, rowOffsets, colOffsets);

        }

        // Black Pawn move right down diagonally
        if (myCurrentrow -1 >=1
                && myCurrentrow -1 <=8
                && myCurrentcol +1 >= 1
                && myCurrentcol +1 <=8
                &&(board.getPiece(new ChessPosition(myCurrentrow - 1, myCurrentcol + 1)) != null)){
            int[] rowOffsets = {-1};
            int[] colOffsets = {1};

            pawnAddPromotion(board, moves, myCurrentrow, myCurrentcol, rowOffsets, colOffsets);

        }



        return moves;
    }

    private static void pawnAddPromotion(ChessBoard board,
                                         ArrayList<ChessMove>moves,
                                         int myCurrentrow,
                                         int myCurrentcol,
                                         int[] rowOffsets,
                                         int[] colOffsets){
        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = myCurrentrow + rowOffsets[i];
            int newCol = myCurrentcol + colOffsets[i];
            // get promoted if the next pawn move is at the baseline
            if (myCurrentrow == 2 ) {
                addMove(moves, myCurrentrow,myCurrentcol,newRow,newCol,true);
            } else {
                addValidMoveCollsions(moves, board, myCurrentrow, myCurrentcol, newRow, newCol);
            }
        }
    }
}