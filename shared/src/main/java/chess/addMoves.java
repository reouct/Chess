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

    public static void addMove (Collection<ChessMove> moves, int startRow, int startCol, int endRow, int endCol, boolean isPromoting) {
        if(isPromoting) {
            moves.add(new ChessMove(new ChessPosition(startRow,startCol), new ChessPosition(endRow,endCol), QUEEN));
            moves.add(new ChessMove(new ChessPosition(startRow,startCol), new ChessPosition(endRow,endCol), KNIGHT));
            moves.add(new ChessMove(new ChessPosition(startRow,startCol),new ChessPosition(endRow,endCol), ROOK));
            moves.add(new ChessMove(new ChessPosition(startRow,startCol),new ChessPosition(endRow,endCol), BISHOP));
        }
        else {
            moves.add(new ChessMove(new ChessPosition(startRow,startCol), new ChessPosition(endRow,endCol), null));
        }
    }

    public static void AddValidMove(ChessBoard board, Collection<ChessMove> moves, int current_row, int current_col, int row, int col ){
        ChessPiece current_piece;
        if ((current_row + row) > 1 && (current_col + col) > 1) {
            current_piece = board.getPiece(new ChessPosition(current_row+row, current_col+col));
            if (current_piece == null || current_piece.getTeamColor() != board.getPiece(new ChessPosition(row,col)).getTeamColor()) {
                addMove(moves, current_row, current_col,current_row+row, current_col + col,false);
            }
        }
    }

    public static boolean addValidMoveCollsions(Collection<ChessMove> moves, ChessBoard board, int current_row, int current_col, int row, int col) {
        ChessPiece current_piece = board.getPiece(new ChessPosition(row,col));
        if (current_piece == null) {
            addMove(moves,current_row,current_col, row, col, false);
        }
        else {
            if (current_piece.getTeamColor() != board.getPiece(new ChessPosition(row, col)).getTeamColor()) {
                addMove(moves,current_row,current_col,row,col,false);
            }
            return true;
        }
        return false;
    }

    public static ArrayList<ChessMove> addBishopMoves(ChessBoard board, ChessPosition myPosition){
    ArrayList<ChessMove> moves = new ArrayList<>();

    int my_currentrow = myPosition.getRow();
    int my_currentcol = myPosition.getColumn();

    // Move up and right
    for (int row = my_currentrow+1, col = my_currentcol+1; row <= 8 && col <=8 ; ++row, ++col){
        if (addValidMoveCollsions(moves,board,my_currentrow,my_currentcol,row,col)){
            break;
        };
    }

    // Move down and right
    for (int row = my_currentrow-1, col = my_currentcol+1; row >= 1 && col <=8 ; --row, ++col ){
        if (addValidMoveCollsions(moves,board,my_currentrow,my_currentcol,row,col)){
            break;
        };
    }

    // Move down and left
    for (int row = my_currentrow-1, col = my_currentcol-1;row >= 1 && col >= 1; --row, --col){
        if (addValidMoveCollsions(moves, board, my_currentrow,my_currentcol,row, col)){
            break;
        }
    }

    // Move Up and left
    for (int row = my_currentrow+1, col = my_currentcol-1; row <= 8 && col >= 1 ; ++row,--col){
        if (addValidMoveCollsions(moves, board, my_currentrow,my_currentcol,row,col)){
            break;
        }
    }

    return moves;

    }

}