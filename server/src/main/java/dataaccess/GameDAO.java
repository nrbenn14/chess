package dataaccess;

import model.GameData;

import java.lang.reflect.Array;
import java.util.ArrayList;


public interface GameDAO {
    public boolean createGame(GameData gameData);
    public GameData readGame(int gameID) throws DataAccessException;
    public void updateGame(GameData gameData) throws DataAccessException;
    public ArrayList<GameData> listGames();
    public void clear();
}
