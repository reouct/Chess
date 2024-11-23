package server.websocket;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.LeaveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WebSocketHandler {
    UserDAO userDao;
    GameDAO gameDao;

    private final ConnectionManager connections = new ConnectionManager();
    private final Map<Integer, ConnectionManager> lobbies = new HashMap<>();

    public WebSocketHandler(UserDAO userDao, GameDAO gameDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String json) throws IOException {
        UserGameCommand command = new Gson().fromJson(json, UserGameCommand.class);
        UserData data = new UserData(command.getUsername(), null, null);
        data = userDao.getUser(data.username());

        if(data == null) {
            String output = "Unauthorized.";
            ErrorMessage message = new ErrorMessage(output);
            connections.cancelSession(session, new Gson().toJson(message));
            return;
        }
        String username = data.username();

        switch (command.getCommandType()) {

            case LEAVE -> {
                LeaveCommand cmd = new Gson().fromJson(json, LeaveCommand.class);
                leave(username, cmd.getGameID());
            }
            case RESIGN -> {

            }
            }
        }

    private void leave(String username, Integer gameID) throws IOException {
        ConnectionManager lobby = getLobby(gameID);

        GameData data = new GameData(gameID,null,null,null,null);
        data = gameDao.getGame(data.gameID());
        GameData updated;
        String color;
        if (username.equals(data.whiteUsername())) {
            updated = new GameData(gameID, null, data.blackUsername(), data.gameName(), data.game());
            color = "white";
        }
        else if (username.equals(data.blackUsername())) {
            updated = new GameData(gameID, data.whiteUsername(), null, data.gameName(), data.game());
            color = "black";
        }
        else {
            updated = data;
            color = "observer";
        }
        gameDao.updateGame(updated);

        lobby.remove(username);
        String output = username+" ("+color+") has left the game.";
        NotificationMessage message = new NotificationMessage(output);
        lobby.broadcast(username, new Gson().toJson(message));
    }

    private void sendServerError(String username, ConnectionManager lobby, DataAccessException e) throws IOException {
        System.out.println(e.getMessage());
        String output = "A server error occurred.";
        ErrorMessage message = new ErrorMessage(output);
        lobby.send(username, new Gson().toJson(message));
    }

    private ConnectionManager getLobby(Integer gameID) {
        if (!lobbies.containsKey(gameID)) {
            lobbies.put(gameID,new ConnectionManager());
        }
        return lobbies.get(gameID);
    }
}

