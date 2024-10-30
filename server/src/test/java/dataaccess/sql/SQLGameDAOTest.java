package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SQLGameDAOTest {

    @BeforeEach
    void setUp() throws SQLException, DataAccessException {
        SQLGameDAO gameDAO = new SQLGameDAO();
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM game")) {
                stmt.executeUpdate();
            }
        }
    }

    @Test
    void clear() {
    }

    @Test
    void createGame() {
        SQLGameDAO gameDAO = new SQLGameDAO();
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1, "whitePlayer", "blackPlayer", "testGame", game);

        // Call the createGame method
        int gameId = gameDAO.createGame(gameData);

        // Verify the game was created
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM game WHERE gameID = ?")) {
                preparedStatement.setInt(1, gameId);
                try (var resultSet = preparedStatement.executeQuery()) {
                    assertTrue(resultSet.next(), "Game should be created");
                    assertEquals(gameData.gameID(), resultSet.getInt("gameID"), "Game ID should match");
                    assertEquals(gameData.whiteUsername(), resultSet.getString("whiteUsername"), "White username should match");
                    assertEquals(gameData.blackUsername(), resultSet.getString("blackUsername"), "Black username should match");
                    assertEquals(gameData.gameName(), resultSet.getString("gameName"), "Game name should match");
                    assertEquals(gameData.game(), new Gson().fromJson(resultSet.getString("game"), ChessGame.class), "Game data should match");
                }
            }
        } catch (SQLException | DataAccessException e) {
            fail("Failed to verify game creation: " + e.getMessage());
        }
    }

    @Test
    void getGame() {
    }

    @Test
    void badGetGame() {
    }

    @Test
    void listGames() {
    }

    @Test
    void badListGames() {
    }

    @Test
    void updateGame() {
    }

    @Test
    void badUpdateGame() {
    }
}