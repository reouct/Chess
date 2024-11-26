package websocket.commands;

public class ConnectCommand extends UserGameCommand{
    private final Integer gameID;

    public ConnectCommand(Integer gameID) {
        this.commandType = CommandType.CONNECT;
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }
}
