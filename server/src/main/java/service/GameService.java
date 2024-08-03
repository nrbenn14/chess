package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class GameService extends Service {

    public GameData createGame(AuthData authData, GameData gameData) throws DataAccessException {
        AuthData authData1 = authDAO.readAuth(authData.getAuthToken());
        String user = null;
        if (authData1 != null) {
            user = authData1.getAuthToken();
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }

        if (gameData.getGameName() == null) {
            throw new DataAccessException("Error: game name required");
        }


        GameData game = new GameData(gameDAO.listGames().size(), null, null, gameData.getGameName(), new ChessGame());
        if (gameDAO.createGame(gameData)) {
            return game;
        }

        throw new DataAccessException("Error: game creation failed");
    }

    public ArrayList<GameData> listGames(AuthData authData) throws DataAccessException {
        AuthData authData1 = authDAO.readAuth(authData.getAuthToken());
        String user = null;
        if (authData1 != null) {
            user = authData1.getAuthToken();
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
        return gameDAO.listGames();
    }

    public void clear() {
        gameDAO.clear();
    }
}
