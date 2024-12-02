package websocket.commands;

public class ResignCommand extends UserGameCommand{
    private Integer gameID;

    public ResignCommand(String authToken, Integer gameID) {
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
        this.authToken = authToken;

    }

    public Integer getGameID(){
        return gameID;
    }

    public void setGameID(Integer gameID){
        this.gameID = gameID;
    }
}
