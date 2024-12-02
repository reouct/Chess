package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private boolean isGameOver;

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", history=" + history +
                ", turn=" + turn +
                ", whiteCM=" + whiteCM +
                ", blackCM=" + blackCM +
                ", whiteSM=" + whiteSM +
                ", blackSM=" + blackSM +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(whiteCM, chessGame.whiteCM) &&
                Objects.equals(blackCM, chessGame.blackCM) &&
                Objects.equals(whiteSM, chessGame.whiteSM) &&
                Objects.equals(blackSM, chessGame.blackSM) &&
                Objects.equals(board, chessGame.board) &&
                Objects.equals(history, chessGame.history) &&
                turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, history, turn, whiteCM, blackCM, whiteSM, blackSM);
    }

    private ChessBoard board;
    private ArrayList<ChessBoard> history = new ArrayList<ChessBoard>();
    private TeamColor turn;
    boolean whiteCM;
    boolean blackCM;
    boolean whiteSM;
    boolean blackSM;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        this.turn = TeamColor.WHITE;
        this.isGameOver = false;
        this.whiteCM = false;
        this.blackCM = false;
        this.whiteSM = false;
        this.blackSM = false;
        updateStatus();

    }

    private boolean isStalemate() {
        if (isInCheck(turn)) {
            return false;
        }

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && piece.getTeamColor() == turn) {
                    if (!validMoves(position).isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void updateStatus() {
        switch (turn) {
            case WHITE:
                whiteSM = isStalemate();
                whiteCM = isStalemate();
                break;
            case BLACK:
                blackSM = isStalemate();
                blackCM = isStalemate();
                break;
        }
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
        updateStatus();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver() {
        isGameOver = true;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        Collection<ChessMove> possibleMoves = piece.pieceMoves(board,startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (ChessMove move : possibleMoves) {
            ChessBoard tempBoard = new ChessBoard(board);
            addMove(move,tempBoard);
            if (!isInCheck(piece.getTeamColor(),tempBoard)) {
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    private void addMove(ChessMove move, ChessBoard board) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece != null) {
            board.removePiece(move.getStartPosition()); // Remove piece from the start

            // Handle promotion: Only promote if the move is valid for a pawn promotion
            if (move.getPromotionPiece() != null && (move.getEndPosition().getRow() == 0 || move.getEndPosition().getRow() == 7)) {
                ChessPiece promoPiece = new ChessPiece(piece.getTeamColor(), move.getPromotionPiece());
                board.addPiece(move.getEndPosition(), promoPiece);
            } else {
                board.addPiece(move.getEndPosition(), piece); // Regular move
            }
        }
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    private boolean isValidMove(ChessMove move) throws InvalidMoveException {
        if (validMoves(move.getStartPosition()) == null) {
            throw new InvalidMoveException();
        }
        if (!validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException();
        }
        if (board.getPiece(move.getStartPosition()).getTeamColor() != turn) {
            throw new InvalidMoveException();
        }
        return true;
    }

    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (isValidMove(move)) {
            ChessBoard newBoard = this.board;
            ChessPiece pieceInQuestion = this.board.getPiece(move.getStartPosition());
            newBoard.removePiece(move.getStartPosition());
            ChessPiece promoPiece = new ChessPiece(pieceInQuestion.getTeamColor(), move.getPromotionPiece());
            if (move.getPromotionPiece() != null) {
                newBoard.addPiece(move.getEndPosition(), promoPiece);
            } else {
                newBoard.addPiece(move.getEndPosition(), pieceInQuestion);
            }
            this.history.add(this.board);
            setBoard(newBoard);
            setTeamTurn(this.turn == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
            updateStatus();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor, this.board);
    }

    private boolean isInCheck(TeamColor teamColor, ChessBoard board) {
        for (int row = 1; row <= 8; ++row) {
            for (int col = 1; col <= 8; ++col) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (isOpponentPiece(piece, teamColor)) {
                    if (canCaptureKing(piece, board, position)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isOpponentPiece(ChessPiece piece, TeamColor teamColor) {
        return piece != null && piece.getTeamColor() != teamColor;
    }

    private boolean canCaptureKing(ChessPiece piece, ChessBoard board, ChessPosition position) {
        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, position);
        for (ChessMove move : pieceMoves) {
            ChessPiece toCapture = board.getPiece(move.getEndPosition());
            if (toCapture != null && toCapture.getPieceType() == ChessPiece.PieceType.KING) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */

    private boolean hasNoValidMoves(TeamColor teamColor) {
        for (int row = 1; row <=8; ++row) {
            for (int col = 1; col <=8; ++col){
                ChessPosition position = new ChessPosition(row, col);
                Collection<ChessMove> moves = validMoves(position);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() == teamColor && moves != null && moves.size() >0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && hasNoValidMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return teamColor == getTeamTurn() && !isInCheck(teamColor) &&hasNoValidMoves(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
