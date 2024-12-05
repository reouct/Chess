package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WebSocketHandler {
    UserDAO userDao;
    GameDAO gameDao;
    AuthDAO authDao;

    private final ConnectionManager connections = new ConnectionManager();
    private final Map<Integer, ConnectionManager> lobbies = new HashMap<>();

    public WebSocketHandler(UserDAO userDao, GameDAO gameDao, AuthDAO authDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
        this.authDao = authDao;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String json) throws IOException {
        UserGameCommand command = new Gson().fromJson(json, UserGameCommand.class);
        AuthData data = authDao.getAuth(command.getAuthString());

        if(data == null) {
            String output = "Unauthorized.";
            ErrorMessage message = new ErrorMessage(output);
            connections.cancelSession(session, new Gson().toJson(message));
            return;
        }
        String username = data.username();

        switch (command.getCommandType()) {

            case JOIN_PLAYER -> {
                JoinPlayerCommand cmd = new Gson().fromJson(json, JoinPlayerCommand.class);
                if (cmd.getPlayerColor() == ChessGame.TeamColor.WHITE){
                    joinPlayer(username, cmd.getGameID(), ChessGame.TeamColor.WHITE, session);
                } else {
                    joinPlayer(username, cmd.getGameID(), ChessGame.TeamColor.BLACK, session);
                }
            }

            case JOIN_OBSERVER -> {
                JoinObserverCommand cmd = new Gson().fromJson(json, JoinObserverCommand.class);
                joinObserver(username, cmd.getGameID(), session);
            }

            case CONNECT -> {
                // Deserialize the command to get the game ID
                ConnectCommand cmd = new Gson().fromJson(json, ConnectCommand.class);
                int gameID = cmd.getGameID();

                // Get the lobby for the game
                ConnectionManager lobby = getLobby(gameID);

                // Add the user to the lobby
                lobby.add(username, session);
                GameData gameData = gameDao.getGame(gameID);
                if (gameData == null || gameData.game() == null) {
                    String output = "Game not found.";
                    ErrorMessage message = new ErrorMessage(output);
                    connections.cancelSession(session, new Gson().toJson(message));
                    return;
                }
                LoadGameMessage message1 = new LoadGameMessage(gameData.game());
                lobby.send(username, new Gson().toJson(message1));

                // Notify other users in the lobby
                String output = username + " has connected to the game.";
                NotificationMessage message = new NotificationMessage(output);
                lobby.broadcast(username, new Gson().toJson(message));

            }
            case MAKE_MOVE -> {
                MakeMoveCommand cmd = new Gson().fromJson(json, MakeMoveCommand.class);
                makeMove(username,cmd.getGameID(), cmd.getMove());

            }
            case LEAVE -> {
                LeaveCommand cmd = new Gson().fromJson(json, LeaveCommand.class);
                leave(username, cmd.getGameID());
            }
            case RESIGN -> {
                ResignCommand cmd = new Gson().fromJson(json, ResignCommand.class);
                resign(username, cmd.getGameID());
            }
            }
        }

    private void joinPlayer(String username, Integer gameID, ChessGame.TeamColor teamColor, Session session) {
        ConnectionManager lobby = getLobby(gameID);
        lobby.add(username, session);

        try {
            GameData data = new GameData(gameID, null, null, null, null);
            data = gameDao.getGame(data.gameID());
            if(data == null) {
                String output = "No such game exists.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }
            ChessGame game = data.game();

            LoadGameMessage loadGameMessage = new LoadGameMessage(game);
            lobby.send(username, new Gson().toJson(loadGameMessage));

            String output = username + " has joined the game as " + teamColor + ".";
            NotificationMessage message = new NotificationMessage(output);
            lobby.broadcast(username, new Gson().toJson(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void joinObserver(String username, Integer gameID, Session session) throws IOException {
        ConnectionManager lobby = getLobby(gameID);
        lobby.add(username, session);

        try {
            GameData data = new GameData(gameID, null, null, null, null);
            data = gameDao.getGame(data.gameID());
            if(data == null) {
                String output = "No such game exists.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }
            ChessGame game = data.game();

            LoadGameMessage loadGameMessage = new LoadGameMessage(game);
            lobby.send(username, new Gson().toJson(loadGameMessage));

            String output = username + " has joined the game as an observer.";
            NotificationMessage message = new NotificationMessage(output);
            lobby.broadcast(username, new Gson().toJson(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void resign(String username, Integer gameID) throws IOException {
        ConnectionManager lobby = getLobby(gameID);

        try {
            GameData data = new GameData(gameID, null, null, null, null);
            data = gameDao.getGame(data.gameID());
            ChessGame game = data.game();

            // Check if the user is a player in the game
            if (!(username.equals(data.whiteUsername())||username.equals(data.blackUsername()))) {
                String output = "You can't resign.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }

            // Check if the game is already over
            if(game.isGameOver()) {
                String output = "Game is ended.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }

            game.setGameOver();
            GameData updated = new GameData(gameID, data.whiteUsername(), data.blackUsername(), data.gameName(), game);
            gameDao.updateGame(updated);

            String output = username+" has resigned.";
            NotificationMessage message = new NotificationMessage(output);
            lobby.broadcast(null, new Gson().toJson(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeMove(String username, Integer gameID, ChessMove move) throws IOException {
        ConnectionManager lobby = getLobby(gameID);

        try {
            GameData data = new GameData(gameID,null,null,null,null);
            data = gameDao.getGame(data.gameID());
            ChessGame game = data.game();

            // Make Invalid Move
            if((game.getTeamTurn() == ChessGame.TeamColor.WHITE && !username.equals(data.whiteUsername()))
                    || (game.getTeamTurn() == ChessGame.TeamColor.BLACK && !username.equals(data.blackUsername()))) {
                String output = "You can't make moves right now.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }

            // Make Move Game Over
            if(game.isGameOver()) {
                String output = "Game has ended.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }

            try {
                game.makeMove(move);
            } catch (InvalidMoveException e) {
                String output = "Invalid Move.";
                ErrorMessage message = new ErrorMessage(output);
                lobby.send(username, new Gson().toJson(message));
                return;
            }

            // Checkmate notification
            if(game.isCheckmate()) {
                String output = "Checkmate! " + (game.getTeamTurn() == ChessGame.TeamColor.WHITE ? "BLACK" : "WHITE") + " wins.";
                NotificationMessage message = new NotificationMessage(output);
                lobby.broadcast(null, new Gson().toJson(message));
            }
            // Stalemate notification
            if (game.isStalemate()) {
                String output = "Stalemate! The game is a draw.";
                NotificationMessage message = new NotificationMessage(output);
                lobby.broadcast(null, new Gson().toJson(message));
            }


            GameData updatedGame = new GameData(gameID, data.whiteUsername(), data.blackUsername(), data.gameName(), game);
            gameDao.updateGame(updatedGame);

            LoadGameMessage loadGameMessage = new LoadGameMessage(game);
            lobby.broadcast(null, new Gson().toJson(loadGameMessage));

            String output = username+" moved " + " from " + move.getStartPosition() + " to "+ move.getEndPosition();
            NotificationMessage message = new NotificationMessage(output);
            lobby.broadcast(username, new Gson().toJson(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        ConnectionManager connection = lobbies.get(gameID);
        connection.remove(username);
        gameDao.updateGame(updated);

        connection.remove(username);
        String output = username+" ("+color+") has left the game.";
        NotificationMessage message = new NotificationMessage(output);
        connection.broadcast(username, new Gson().toJson(message));
    }


    private ConnectionManager getLobby(Integer gameID) {
        if (!lobbies.containsKey(gameID)) {
            lobbies.put(gameID,new ConnectionManager());
        }
        return lobbies.get(gameID);
    }
}

