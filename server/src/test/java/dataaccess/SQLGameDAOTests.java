package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;


public class SQLGameDAOTests {
    private static GameData game;
    private static SQLGameDAO sqlGameDAO;
    private static Gson GSON;

    @BeforeAll
    public static void init() throws Exception {
        game = new GameData(1, "tron", "sark", "the grid", new ChessGame());
        sqlGameDAO = new SQLGameDAO();
        GSON = new Gson();
        DatabaseManager.createDatabase();
    }

    @BeforeEach
    public void setup() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            truncateTable(connection, "user");
            truncateTable(connection, "auth");
            truncateTable(connection, "game");
        }
        catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    private static void truncateTable(Connection connection, String string) throws SQLException {
        var statement = connection.prepareStatement("TRUNCATE TABLE " + string);
        statement.executeUpdate();
    }

    @Test
    @DisplayName("Create Game Test")
    public void createGame() throws Exception {
        sqlGameDAO.createGame(game);
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM game");
            var result = statement.executeQuery();
            result.next();

            int gameID = result.getInt("gameID");
            Assertions.assertEquals(1, gameID);

            String whiteUsername = result.getString("whiteUsername");
            Assertions.assertEquals("tron", whiteUsername);

            String blackUsername = result.getString("blackUsername");
            Assertions.assertEquals("sark", blackUsername);

            String gameName = result.getString("gameName");
            Assertions.assertEquals("the grid", gameName);

            String game =  result.getString("chessGame");
            String gameJSON = GSON.toJson(new ChessGame());
            Assertions.assertEquals(game, gameJSON);

        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Test
    @DisplayName("No Game Name Creation")
    public void nullGameName() throws Exception {
        GameData gameData = new GameData(2, "flynn", "clu", "", new ChessGame());
        sqlGameDAO.createGame(game);
        sqlGameDAO.createGame(gameData);

        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM game WHERE gameName = \"\"");
            var result = statement.executeQuery();
            result.next();

            String gameName = result.getString("gameName");
            Assertions.assertEquals("", gameName);
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }
}
