package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.interfaces.AuthDAO;
import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {

//    private void MySqlDataAccess() throws DataAccessException{
//        configureDatabase(createStatements);
//    }
//
//    private

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
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE TRUE")) {
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
        return "";
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
    public AuthData getAuth(AuthData data) {
        return null;
    }

    @Override
    public AuthData getAuth(String data) {
        return null;
    }

//    private final String[] createStatements = {
//            """
//            CREATE TABLE IF NOT EXISTS  auth (
//              `authToken` varchar(256) NOT NULL,
//              `username` varchar(256) NOT NULL,
//              PRIMARY KEY (authToken)
//            )
//            """
//    };

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `id` int NOT NULL AUTO_INCREMENT,
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (authToken)
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
