package dataaccess;

import model.GameData;

import java.lang.reflect.Array;
import java.util.ArrayList;


public interface GameDAO {
    boolean createGame(GameData gameData);
    GameData readGame(int gameID) throws DataAccessException;
    void updateGame(GameData gameData) throws DataAccessException;
    ArrayList<GameData> listGames();
    void clear();
}
