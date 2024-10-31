package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.AuthDAO;
import model.AuthData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE auth")) {
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String createAuth(String username) {
        String newToken = UUID.randomUUID().toString();
        String sql = "INSERT INTO auth (authtoken, username) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, newToken);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
        return newToken;
    }

    @Override
    public void deleteAuth(AuthData data) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE authtoken=? ")) {
                preparedStatement.setString(1, data.authToken());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData getAuth(String authToken) {
       String sql = "SElECT * FROM auth WHERE authtoken=?";
       try (var conn = DatabaseManager.getConnection()) {
           try (var preparedStatement = conn.prepareStatement(sql)) {
               preparedStatement.setString(1,authToken);

               ResultSet rs = preparedStatement.executeQuery();
               if (rs.next()) {
                   String authtoken = rs.getString("authtoken");
                   String username = rs.getString("username");
                   return new AuthData(authtoken, username);
               }
               else {
                   return null;
               }
           }
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }


    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (authToken)
            )
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseConfigurator.configureDatabase(createStatements);
    }
}
