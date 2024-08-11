package dataaccess;

import model.GameData;

import java.util.ArrayList;

public class SQLGameDAO implements GameDAO {
    @Override
    public boolean createGame(GameData gameData) {
        return false;
    }

    @Override
    public GameData readGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {

    }

    @Override
    public ArrayList<GameData> listGames() {
        return null;
    }

    @Override
    public void clear() {

    }
}
