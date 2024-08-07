package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import service.GameService;

import java.lang.reflect.Executable;
import java.util.concurrent.atomic.DoubleAdder;

public class GameServiceTests {
    private static GameService gameService;
    private static AuthData user;
    private static MemoryGameDAO gameDAO;
    private static MemoryGameDAO gameDAO1;
    private static GameData game1;
    private static GameData game2;

    @BeforeAll
    public static void init() {
        user = new AuthData();
        user.setAuthToken("54321");
        user.setUsername("flynn");
        gameDAO = new MemoryGameDAO();
        game1 = new GameData(0, null, null, "game1", new ChessGame());
        game2 = new GameData(1, null, null, "game2", new ChessGame());
    }

    @BeforeEach
    public void setup() throws Exception {
        gameDAO = new MemoryGameDAO();
        gameService = new GameService();
        gameDAO1 = new MemoryGameDAO();
        gameService.setGameDAO(gameDAO1);
        gameService.getAuthDAO().clear();
        gameService.getAuthDAO().createAuth(user);
        gameService.getGameDAO().createGame(game1);
        gameService.getGameDAO().createGame(game2);
        gameDAO.clear();
    }

    @Test
    @DisplayName("Join Game Service")
    public void joinGame() throws Exception {
        Assertions.assertTrue(gameService.joinGame(user, new GameData(1, "whiteUser", null, null, null)));
        Assertions.assertTrue(gameService.joinGame(user, new GameData(1, null, "blackUser", null, null)));
        Assertions.assertEquals("flynn", gameService.getGameDAO().readGame(1).getWhiteUsername());
        Assertions.assertEquals("flynn", gameService.getGameDAO().readGame(1).getBlackUsername());
    }

    @Test
    @DisplayName("Fake Game Test")
    public void joinFakeGame() throws Exception {
        GameData gameData = new GameData(3, "whiteUser1", null, null, null);
        AuthData authData = new AuthData();
        authData.setUsername("tron");
        authData.setAuthToken("09876");

        Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(authData, gameData));
    }

    @Test
    @DisplayName("Create Game Test")
    public void createGame() throws Exception {
        GameData gameData = new GameData(3, null, null, "newGame", null);
        gameService.createGame(user, gameData);

        Assertions.assertNotNull(gameService.listGames(user).getLast());
        Assertions.assertEquals("newGame", gameService.listGames(user).getLast().getGameName());
    }

    @Test
    @DisplayName("Bad Auth Create Game")
    public void badCreateGame() {
        GameData gameData = new GameData(3, null, null, "newGame", null);
        AuthData authData = new AuthData();
        authData.setUsername("tron");
        authData.setAuthToken("09876");

        Assertions.assertThrows(DataAccessException.class, () -> gameService.createGame(authData, gameData));
    }

    @Test
    @DisplayName("List Games Test")
    public void listGames() throws Exception {
        gameDAO.createGame(game1);
        gameDAO.createGame(game2);
        Assertions.assertEquals(gameDAO.listGames(), gameService.listGames(user));
    }

    @Test
    @DisplayName("Bad Auth List Games")
    public void badListGames() throws Exception {
        gameDAO.createGame(game1);
        gameDAO.createGame(game2);

        AuthData authData = new AuthData();
        authData.setUsername("tron");
        authData.setAuthToken("09876");

        Assertions.assertThrows(DataAccessException.class, () -> gameService.listGames(authData));
    }

    @Test
    @DisplayName("Clear Test")
    public void clearGames() throws Exception {
        gameService.clear();
        Assertions.assertEquals(gameDAO.listGames(), gameService.listGames(user));
    }
}
