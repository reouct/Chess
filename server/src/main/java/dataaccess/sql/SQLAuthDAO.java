package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.AuthDAO;
import model.AuthData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLAuthDAO implements AuthDAO {
    private static final String URL = "jdbc:your_database_url";
    private static final String USER = "your_database_user";
    private static final String PASSWORD = "your_database_password";

    @Override
    public void clear() {
        String sql = "DELETE FROM auth";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                if (preparedStatement.executeUpdate() >= 1) {
                    System.out.println("Cleared authtoken table");
                }
                else {
                    System.out.println("Failed to clear authtoken table");
                }
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void createAuth(AuthData data) {
        // need implementations
    }

    @Override
    public void deleteAuth(AuthData data) {
        // need implementations
    }

    @Override
    public void getUser(AuthData data) {
        // need implementations
    }
}
