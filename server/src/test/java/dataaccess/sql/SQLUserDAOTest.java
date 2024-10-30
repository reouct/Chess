package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {

    private SQLUserDAO userDAO;

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        userDAO = new SQLUserDAO();
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM user")) {
                stmt.executeUpdate();
            }
        }
    }

    @Test
    void clear() {
    }

    @Test
    void createUser() throws SQLException, DataAccessException {
        String username = "testUser";
        String password = "testPass";
        String email = "test@example.com";

        // Create test user data
        UserData userData = new UserData(username, password, email);

        // Call the createUser method
        userDAO.createUser(userData);

        // Retrieve user from the database
        UserData retrievedUser = userDAO.getUser(username);

        // Verify user data
        assertNotNull(retrievedUser);
        assertEquals(username, retrievedUser.username());
        assertEquals(password, retrievedUser.password());
        assertEquals(email, retrievedUser.email());
    }

    @Test
    void badCreateUser() {
    }

    @Test
    void getUser() throws SQLException, DataAccessException {
        String username = "testUser";
        String password = "testPass";
        String email = "test@example.com";

        // Insert test user
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?)")) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.executeUpdate();
            }
        }

        // Retrieve user
        UserData user = userDAO.getUser(username);

        // Verify user data
        assertNotNull(user);
        assertEquals(username, user.username());
        assertEquals(password, user.password());
        assertEquals(email, user.email());
    }

    @Test
    void badGetUser() {
    }
}