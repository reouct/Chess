package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    private ArrayList<ChessBoard> history = new ArrayList<ChessBoard>();
    private TeamColor turn;
    boolean whiteCM;
    boolean blackCM;
    boolean whiteSM;
    boolean blackSM;

    public ChessGame() {
        board = new ChessBoard();
        this.turn = TeamColor.WHITE;
        this.whiteCM = false;
        this.blackCM = false;
        this.whiteSM = false;
        this.blackSM = false;
        updateStatus();

    }

    private boolean isStalemate() {
        if (isInCheck(turn)) return false;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition testPos = new ChessPosition(i, j);
                ChessPiece testPiece = board.getPiece(testPos);
                if (testPiece != null) {
                    if (testPiece.getTeamColor() == turn && !validMoves(testPos).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isCheckmate() {
        if (!isInCheck(turn)) return false;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition testPos = new ChessPosition(i, j);
                ChessPiece testPiece = board.getPiece(testPos);
                if (testPiece != null) {
                    if (testPiece.getTeamColor() == turn && !validMoves(testPos).isEmpty()) {
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
                whiteCM = isCheckmate();
                break;
            case BLACK:
                blackSM = isStalemate();
                blackCM = isCheckmate();
                break;
        }
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        throw new RuntimeException("Not implemented");
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
    public Collection<ChessMove> validMoves(ChessPosition startPosition){
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> possibleMoves = piece.pieceMoves(board,startPosition);

        for (ChessMove move : possibleMoves) {
            ChessBoard newBoard = new ChessBoard(board);
            addMove(move, newBoard);
            if (!isInCheck(piece.getTeamColor(), newBoard)) {
                validMoves.add(move);
            }

        }

        return validMoves;
    }

    private void addMove(ChessMove move, ChessBoard board) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece != null) {
            board.addPiece(move.getEndPosition(), piece);
            if (move.getPromotionPiece() != null) {
                board.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), move.getPromotionPiece()));
            }
            board.addPiece(move.getStartPosition(), null);
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (validMoves(move.getStartPosition()) == null) {
            throw new InvalidMoveException();
        } else if (!validMoves(move.getStartPosition()).contains(move)) {
            throw new InvalidMoveException();
        } else if (board.getPiece(move.getStartPosition()).getTeamColor() != turn) {
            throw new InvalidMoveException();
        } else {
            ChessBoard newBoard = this.board;
            ChessPiece pieceInQuestion = this.board.getPiece(move.getStartPosition());
            newBoard.removePiece(move.getStartPosition());
            ChessPiece promoPiece = new ChessPiece(pieceInQuestion.getTeamColor(), move.getPromotionPiece());
            if (move.getPromotionPiece() != null) {
                newBoard.addPiece(move.getEndPosition(), promoPiece);
            } else newBoard.addPiece(move.getEndPosition(), pieceInQuestion);
            this.history.add(this.board);
            setBoard(newBoard);
            if (this.turn == TeamColor.WHITE) setTeamTurn(TeamColor.BLACK);
            else setTeamTurn(TeamColor.WHITE);
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
                ChessPosition position = new ChessPosition(row,col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getTeamColor() != teamColor) {
                        Collection<ChessMove> pieceMoves = piece.pieceMoves(board, position);
                        for (ChessMove move : pieceMoves) {
                            ChessPiece toCapture = board.getPiece(move.getEndPosition());
                            if (toCapture != null) {
                                if (toCapture.getPieceType() == ChessPiece.PieceType.KING) {
                                    return true;
                                }
                            }
                        }
                    }
                }
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
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
