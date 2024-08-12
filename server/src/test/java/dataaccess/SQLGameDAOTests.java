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
        game = new GameData(1, null, null, "the grid", new ChessGame());
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
//            Assertions.assertEquals("tron", whiteUsername);
            Assertions.assertNull(whiteUsername);

            String blackUsername = result.getString("blackUsername");
//            Assertions.assertEquals("sark", blackUsername);
            Assertions.assertNull(blackUsername);

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
    @DisplayName("Read Game")
    public void readGame() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)");


            statement.setString(1, game.getWhiteUsername());
            statement.setString(2, game.getBlackUsername());
            statement.setString(3, game.getGameName());
            statement.setString(4, GSON.toJson(game.getGame()));
            statement.executeUpdate();

            GameData gameData = sqlGameDAO.readGame(1);

            Assertions.assertEquals(1, gameData.getGameID());
            Assertions.assertNull(gameData.getWhiteUsername());
            Assertions.assertNull(gameData.getBlackUsername());
            Assertions.assertEquals("the grid", gameData.getGameName());
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Test
    @DisplayName("Read Game Fail")
    public void readGameFail() throws Exception {
        sqlGameDAO.createGame(game);
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM game where gameID = 3");
            var result = statement.executeQuery();

            Assertions.assertFalse(result.isBeforeFirst());
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Test
    @DisplayName("No Game Name Creation")
    public void nullGameName() throws Exception {
        GameData gameData = new GameData(2, "flynn", "clu", "", new ChessGame());
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

    @Test
    @DisplayName("Update Game")
    public void updateGame() throws Exception {
        sqlGameDAO.createGame(game);
        GameData gameData = new GameData(1, "sam", "rinzler", "the grid", new ChessGame());
        sqlGameDAO.updateGame(gameData);

        Assertions.assertEquals(1, sqlGameDAO.readGame(1).getGameID());
        Assertions.assertEquals("sam", sqlGameDAO.readGame(1).getWhiteUsername());
        Assertions.assertEquals("rinzler", sqlGameDAO.readGame(1).getBlackUsername());
        Assertions.assertEquals("the grid", sqlGameDAO.readGame(1).getGameName());
    }

    @Test
    @DisplayName("Update Game Fail")
    public void updateGameFail() throws Exception {
        Assertions.assertThrows(DataAccessException.class, () -> sqlGameDAO.updateGame(game));
    }
}
