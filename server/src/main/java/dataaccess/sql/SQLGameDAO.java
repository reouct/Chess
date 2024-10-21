package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.GameDAO;
import model.GameData;

import java.util.Set;

public class SQLGameDAO implements GameDAO {


    @Override
    public void clear() {
        String sql = "DELETE FROM game";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                if (preparedStatement.executeUpdate() >= 1) {
                    System.out.println("Cleared game table");
                }
                else {
                    System.out.println("Failed to clear game table");
                }
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void createGame(GameData data) {

    }

    @Override
    public GameData getGame(GameData data) {
        return null;
    }

    @Override
    public Set<GameData> listGames() {
        return Set.of();
    }

    @Override
    public void updateGame(GameData data) {

    }
}
