package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import javax.net.ssl.SSLException;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

public class SQLGameDAO implements GameDAO {

    private final Gson gson = new Gson();

    static {
        try {
            DatabaseManager.createDatabase();
        }
        catch (DataAccessException dataAccessException) {
            throw new RuntimeException(dataAccessException.getMessage());
        }
    }

    @Override
    public boolean createGame(GameData gameData) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO game (whiteUsername, blackUsername, gameName, chessGame) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setNull(1, Types.VARCHAR);
//            statement.setString(1, gameData.getWhiteUsername());
            statement.setNull(2, Types.VARCHAR);
//            statement.setString(2, gameData.getBlackUsername());
            statement.setString(3, gameData.getGameName());

            var gameJSON = gson.toJson(gameData.getGame());
            statement.setString(4, gameJSON);
            statement.executeUpdate();

            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gameData.setGameID(generatedKeys.getInt(1));
                }
                else {
                    throw new DataAccessException("Error: no ID obtained.");
                }
            }
            return true;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public GameData readGame(int gameID) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT whiteUsername, blackUsername, gameName, chessGame FROM game WHERE gameID = ?");
            statement.setInt(1, gameID);
            var result = statement.executeQuery();

            if (result.next()) {
                return new GameData(
                        gameID,
                        result.getString("whiteUsername"),
                        result.getString("blackUsername"),
                        result.getString("gameName"),
                        gson.fromJson(result.getString("ChessGame"), ChessGame.class)
                );
            }
            throw new DataAccessException("Error: invalid game ID");
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement(
                    "UPDATE game SET whiteUsername = ?, blackUsername = ?, gameName = ?, chessGame = ? WHERE gameID = ?");
            statement.setString(1, gameData.getWhiteUsername());
            statement.setString(2, gameData.getBlackUsername());
            statement.setString(3, gameData.getGameName());
            statement.setString(4, gson.toJson(gameData.getGame()));
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
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM game");
            var result = statement.executeQuery();

            ArrayList<GameData> gameList = new ArrayList<>();
            while (result.next()) {
                gameList.add(new GameData(
                        result.getInt("gameID"),
                        result.getString("whiteUsername"),
                        result.getString("blackUsername"),
                        result.getString("gameName"),
                        gson.fromJson(result.getString("chessGame"), ChessGame.class))
                );
            }
            return gameList;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("TRUNCATE TABLE game");
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }
}
