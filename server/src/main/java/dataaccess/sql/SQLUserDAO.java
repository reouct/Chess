package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.UserDAO;
import model.UserData;

public class SQLUserDAO implements UserDAO {
    @Override
    public void clear() {
        String sql = "DELETE FROM user";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                if (preparedStatement.executeUpdate() >= 1) {
                    System.out.println("Cleared user table");
                }
                else {
                    System.out.println("Failed to clear user table");
                }
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void createUser(UserData data) {
        // need implementation
    }

    @Override
    public UserData getUser(UserData data) {
        // need implementation
        return null;
    }
}
