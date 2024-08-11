package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import javax.net.ssl.SSLException;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAO {

    private final Gson GSON = new Gson();
    @Override
    public boolean createGame(GameData gameData) throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)");

            statement.setString(1, gameData.getWhiteUsername());
            statement.setString(2, gameData.getBlackUsername());
            statement.setString(3, gameData.getGameName());

            var gameJSON = GSON.toJson(gameData.getGame());
            statement.setString(4, gameJSON);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public GameData readGame(int gameID) throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT whiteUsername, blackUsername, gameName, chessGame FROM game WHERE gameID = ?");
            statement.setInt(1, gameID);
            var result = statement.executeQuery();

            if (result.next()) {
                String whiteUsername = result.getString("whiteUsername");
                String blackUsername = result.getString("blackUsername");
                String gameName = result.getString("gameName");
                ChessGame game = GSON.fromJson(result.getString("ChessGame"), ChessGame.class);

                return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
            }
            throw new DataAccessException("Error: invalid game ID");
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("UPDATE game SET whiteUsername = ?, blackUsername = ?, gameName = ?, chessGame = ? WHERE gameID = ?");
            statement.setString(1, gameData.getWhiteUsername());
            statement.setString(2, gameData.getBlackUsername());
            statement.setString(3, gameData.getGameName());
            statement.setString(4, GSON.toJson(gameData.getGame()));
            statement.setInt(5, gameData.getGameID());

            if (statement.executeUpdate() <= 0) {
                throw new DataAccessException("Error: game does not exist");
            }
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        DatabaseManager.createDatabase();
        ArrayList<GameData> gameList = new ArrayList<>();
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM game");
            var result = statement.executeQuery();

            while (result.next()) {
                gameList.add(new GameData(result.getInt("gameID"), result.getString("whiteUsername"), result.getString("blackUsername"),
                        result.getString("gameName"), GSON.fromJson(result.getString("chessGame"), ChessGame.class)));
            }
            return gameList;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("TRUNCATE TABLE game");
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }
}
