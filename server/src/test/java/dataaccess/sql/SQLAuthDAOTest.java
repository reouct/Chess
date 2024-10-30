package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLAuthDAOTest {

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        SQLAuthDAO authDAO = new SQLAuthDAO();
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
    void createAuth() {
    }

    @Test
    void badCreateAuth() {
    }

    @Test
    void deleteAuth_Success() {
    }

    // Test case for failed deletion
    @Test
    void deleteAuth_Failure() {
    }

    @Test
    void getAuth() {

    }

    @Test
    void badGetAuth() {
    }
}