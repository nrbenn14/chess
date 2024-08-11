package dataaccess;

import model.GameData;

import java.lang.reflect.Array;
import java.util.ArrayList;


public interface GameDAO {
    boolean createGame(GameData gameData) throws DataAccessException;
    GameData readGame(int gameID) throws DataAccessException;
    void updateGame(GameData gameData) throws DataAccessException;
    ArrayList<GameData> listGames() throws DataAccessException;
    void clear() throws DataAccessException;
}
