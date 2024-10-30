package dataaccess.sql;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.GameDAO;
import model.GameData;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Set;

public class SQLGameDAO implements GameDAO {

//    private void MySqlDataAccess() throws DataAccessException{
//        configureDatabase(createStatements);
//    }
//
//    private SQLGameDAO() throws DataAccessException {
//        MySqlDataAccess();
//    }

    public SQLGameDAO(){
        try {
            configureDatabase();
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
        return null;
    }

    @Override
    public Set<GameData> listGames() {
        return Set.of();
    }

    @Override
    public void updateGame(GameData data) {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS game (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `game` varchar(2048),
              PRIMARY KEY (`gameID`)
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)){
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
