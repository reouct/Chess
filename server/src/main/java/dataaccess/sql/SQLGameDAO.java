package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.GameDAO;
import model.GameData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class SQLGameDAO implements GameDAO {

    public SQLGameDAO(){
        try {
            configureGameDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE game")) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int createGame(GameData data) {
        String sql = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1,data.gameID());
                preparedStatement.setString(2, data.whiteUsername());
                preparedStatement.setString(3, data.blackUsername());
                preparedStatement.setString(4, data.gameName());
                preparedStatement.setString(5, new Gson().toJson(data.game()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return data.gameID();
    }

    @Override
    public GameData getGame(int gameID) {
        String sql = "SELECT * FROM game WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, gameID);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        return new GameData(
                                rs.getInt("gameID"),
                                rs.getString("whiteUsername"),
                                rs.getString("blackUsername"),
                                rs.getString("gameName"),
                                new Gson().fromJson(rs.getString("game"), ChessGame.class)
                        );
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<GameData> listGames() {
        String sql = "SELECT * FROM game";
        Set<GameData> games = new HashSet<>();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        GameData gameData = new GameData(
                                rs.getInt("gameID"),
                                rs.getString("whiteUsername"),
                                rs.getString("blackUsername"),
                                rs.getString("gameName"),
                                new Gson().fromJson(rs.getString("game"), ChessGame.class)
                        );
                        games.add(gameData);
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return games;
    }

    @Override
    public void updateGame(GameData data) {
        String sql = "UPDATE game SET whiteUsername = ?, blackUsername = ?, gameName = ?, game = ? WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, data.whiteUsername());
                preparedStatement.setString(2, data.blackUsername());
                preparedStatement.setString(3, data.gameName());
                preparedStatement.setString(4, new Gson().toJson(data.game()));
                preparedStatement.setInt(5, data.gameID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLatestGameID() {
        String sql = "SELECT MAX(gameID) AS maxID FROM game";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return rs.getInt("maxID");
                } else {
                    return 0;
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `game` longtext,
              PRIMARY KEY (`gameID`)
            )
            """
    };

    private void configureGameDatabase() throws DataAccessException {
        DatabaseConfigurator.configureDatabase(createStatements);
    }
}
