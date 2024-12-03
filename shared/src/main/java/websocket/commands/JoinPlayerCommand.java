package websocket.commands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand {
    private final Integer gameID;
    private final ChessGame.TeamColor playerColor;

    public JoinPlayerCommand(String auth, Integer gameID, ChessGame.TeamColor playerColor) {
        this.commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
        this.authToken = auth;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}
