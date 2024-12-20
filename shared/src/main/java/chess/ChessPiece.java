package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static chess.AddMoves.addBlackPawnMoves;
import static chess.AddMoves.addWhitePawnMoves;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN,
        HIGHLIGHT
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessPiece piece = (ChessPiece) o;
        return pieceColor == piece.pieceColor && type == piece.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();

        switch (type) {
            case BISHOP:
                moves = AddMoves.addBishopMoves(board, myPosition);
                break;
            case QUEEN:
                moves = AddMoves.addQueenMoves(board,myPosition);
                break;
            case ROOK:
                moves = AddMoves.addRookMoves(board, myPosition);
                break;
            case KING:
                moves = AddMoves.addKingMoves(board, myPosition);
                break;
            case KNIGHT:
                moves = AddMoves.addKnightMoves(board, myPosition);
                break;
            case PAWN:
                if (this.getTeamColor() == ChessGame.TeamColor.WHITE){
                    moves = addWhitePawnMoves(board, myPosition);
                } else {
                    moves = addBlackPawnMoves(board, myPosition);
                }
                break;
            default:
                moves = new ArrayList<>();
        }
        return moves;
    }
    }
