package websocket.commands;

public class JoinObserverCommand extends UserGameCommand{
    private final Integer gameID;

    public JoinObserverCommand(String authToken,Integer gameID) {
        this.commandType = CommandType.JOIN_OBSERVER;
        this.authToken = authToken;
        this.gameID = gameID;
    }
    public Integer getGameID() {
        return gameID;
    }
}
