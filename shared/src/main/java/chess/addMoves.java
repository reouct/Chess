package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static chess.ChessPiece.PieceType.QUEEN;
import static chess.ChessPiece.PieceType.BISHOP;
import static chess.ChessPiece.PieceType.KNIGHT;
import static chess.ChessPiece.PieceType.ROOK;

public class addMoves {

    public static ArrayList<ChessMove> addBishopMoves(ChessBoard board, ChessPosition myPosition){
    ArrayList<ChessMove> moves = new ArrayList<>();

    int my_currentrow = myPosition.getRow();
    int my_currentcol = myPosition.getColumn();

    for (int row = my_currentrow+1, col = my_currentcol+1; row <= 8 && col <=8 ; ++row, ++col){
        moves.add(new ChessMove(new ChessPosition(row, col),myPosition,null));
        //moves.add(new ChessMove(new ChessPosition(x,y),myPosition,null));
    }

    //System.out.println(Arrays.toString(moves));

    for (int row = my_currentrow-1, col = my_currentcol+1; row <= 8 && col <=8 ; --row, ++col ){
        moves.add(new ChessMove(new ChessPosition(row,col),myPosition,null));
    }


    for (int row = my_currentrow-1, col = my_currentcol-1;row <= 8 && col <= 8; --row, --col){
        moves.add(new ChessMove(new ChessPosition(row,col),myPosition,null));
    }

    for (int row = my_currentrow+1, col = my_currentcol-1; row <= 8 && col <=8; ++row,--col){
        moves.add(new ChessMove(new ChessPosition(row,col),myPosition,null));
    }

    return moves;

    }

}