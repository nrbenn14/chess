package service;

import chess.ChessGame;
import dataaccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import service.GameService;

public class GameServiceTests {
    private static GameService gameService;
    private static AuthData user;
    private static MemoryGameDAO gameDAO;
    private static GameData game1;
    private static GameData game2;

    @BeforeAll
    public static void init() {
        user = new AuthData();
        user.setAuthToken("54321");
        user.setUsername("flynn");
        gameDAO = new MemoryGameDAO();
        game1 = new GameData(0, null, null, "game1", new ChessGame());
        game1 = new GameData(1, null, null, "game2", new ChessGame());
    }

    @BeforeEach
    public void setup() throws Exception {
        gameService = new GameService();
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
}
