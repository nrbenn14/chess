package dataaccess;

import model.GameData;


public interface GameDAO {
    public boolean createGame(GameData gameData);
    public GameData readGame(int gameID) throws DataAccessException;
    public void updateGame(GameData gameData) throws DataAccessException;
    public void clear();
}
