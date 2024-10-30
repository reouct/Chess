package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {

    @Test
    void clear() {
        SQLAuthDAO authDAO = new SQLAuthDAO();

        // Insert test data
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)")) {
                preparedStatement.setString(1, "testToken");
                preparedStatement.setString(2, "testUser");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            fail("Failed to insert test data: " + e.getMessage());
        }

        // Call the clear method
        try {
            authDAO.clear();
        } catch (DataAccessException e) {
            fail("Failed to clear auth table: " + e.getMessage());
        }

        // Verify the table is empty
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM auth")) {
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        assertEquals(0, count, "Auth table should be empty after clear");
                    }
                }
            }
        } catch (SQLException e) {
            fail("Failed to verify auth table is empty: " + e.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createAuth() {
    }

    @Test
    void deleteAuth_Success() {
        SQLAuthDAO authDAO = new SQLAuthDAO();

        // Insert test data
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)")) {
                preparedStatement.setString(1, "testToken");
                preparedStatement.setString(2, "testUser");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            fail("Failed to insert test data: " + e.getMessage());
        }

        // Create AuthData object to delete
        AuthData authData = new AuthData("testToken", "testUser");

        // Call the deleteAuth method
        try {
            authDAO.deleteAuth(authData);
        } catch (DataAccessException e) {
            fail("Failed to delete auth data: " + e.getMessage());
        }

        // Verify the data is deleted
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM auth WHERE authToken = ?")) {
                preparedStatement.setString(1, "testToken");
                try (var resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        assertEquals(0, count, "Auth data should be deleted");
                    }
                }
            }
        } catch (SQLException e) {
            fail("Failed to verify auth data is deleted: " + e.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // Test case for failed deletion
    @Test
    void deleteAuth_Failure() {
//        SQLAuthDAO authDAO = new SQLAuthDAO();
//
//        // Create AuthData object with non-existent token
//        AuthData authData = new AuthData("nonExistentToken", "testUser");
//
//        // Call the deleteAuth method and expect an exception
//        assertThrows(DataAccessException.class, () -> {
//            authDAO.deleteAuth(authData);
//        }, "Expected DataAccessException when deleting non-existent auth data");
    }

    @Test
    void getAuth() {
        SQLAuthDAO authDAO = new SQLAuthDAO();

        // Insert test data
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)")) {
                preparedStatement.setString(1, "testToken");
                preparedStatement.setString(2, "testUser");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            fail("Failed to insert test data: " + e.getMessage());
        }

        // Call the getAuth method
        AuthData authData = authDAO.getAuth("testToken");

        // Verify the returned AuthData
        assertNotNull(authData, "AuthData should not be null");
        assertEquals("testToken", authData.authToken(), "Auth token should match");
        assertEquals("testUser", authData.username(), "Username should match");
    }

    @Test
    void testGetAuth() {
    }
}