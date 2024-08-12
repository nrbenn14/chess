package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class GameService extends Service {

    AuthService authService = new AuthService();

    public boolean joinGame(AuthData authData, GameData gameData) throws DataAccessException {
        String user = authService.authTokenAuthentication(authData.getAuthToken());
        GameData game = gameDAO.readGame(gameData.getGameID());

        if (game == null) {
            throw new DataAccessException("Error: game does not exist");
        }
        // See if one or both usernames are not taken, add player to game depending on result
        if (gameData.getWhiteUsername() != null && gameData.getBlackUsername() == null) {
            if (game.getWhiteUsername() == null) {
                GameData joinedGame = new GameData(game.getGameID(), user, game.getBlackUsername(), game.getGameName(), game.getGame());
//                game.setWhiteUsername(user);
                gameDAO.updateGame(joinedGame);
                return true;
            }
            else if (game.getWhiteUsername().equals(user)) {
                return true;
            }

            throw new DataAccessException("Error: team already taken");
        }

        else if (gameData.getBlackUsername() != null && gameData.getWhiteUsername() == null) {
            if (game.getBlackUsername() == null) {
                GameData joinedGame = new GameData(game.getGameID(), game.getWhiteUsername(), user, game.getGameName(), game.getGame());
//                game.setBlackUsername(user);
                gameDAO.updateGame(joinedGame);
                return true;
            }

            else if (game.getBlackUsername().equals(user)) {
                return true;
            }

            throw new DataAccessException("Error: team already taken");
        }


        throw new DataAccessException("Error: invalid team assignment");
    }

    public GameData createGame(AuthData authData, GameData gameData) throws DataAccessException {
        String user = authService.authTokenAuthentication(authData.getAuthToken());

        if (gameData.getGameName() == null) {
            throw new DataAccessException("Error: game name required");
        }

        String whitePlayer = gameData.getWhiteUsername() != null ? gameData.getWhiteUsername() : user;
        String blackPlayer = gameData.getBlackUsername() != null ? gameData.getBlackUsername() : user;

//        String whitePlayer = null;
//        String blackPlayer = null;

//        String whitePlayer = "";
//        String blackPlayer = "";

//        if (gameData.getWhiteUsername() == null && gameData.getBlackUsername() != null) {
//            blackPlayer = user;
//        }
//        if (gameData.getBlackUsername() == null && gameData.getWhiteUsername() != null) {
//            whitePlayer = user;
//        }

        GameData game = new GameData(gameDAO.listGames().size(), whitePlayer, blackPlayer, gameData.getGameName(), new ChessGame());
        if (gameDAO.createGame(game)) {
            return game;
        }

        throw new DataAccessException("Error: game creation failed");
    }

    public ArrayList<GameData> listGames(AuthData authData) throws DataAccessException {
        String user = authService.authTokenAuthentication(authData.getAuthToken());
        return gameDAO.listGames();
    }

    public void clear() throws DataAccessException {
        gameDAO.clear();
    }
}
