package dataaccess;

import model.GameData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO {
    public ArrayList<GameData> data;
    private static MemoryGameDAO memoryGameDAO;

    public MemoryGameDAO() {
        data = new ArrayList<GameData>();
    }

    @Override
    public boolean createGame(GameData gameData) {
        if (gameData.getGameID() == data.size()) {
            data.add(gameData);
            return true;
        }
        return false;
    }

    @Override
    public GameData readGame(int gameID) throws DataAccessException {
        if (gameID < 0 || gameID >= data.size()) {
            throw new DataAccessException("Invalid game ID");
        }

        return data.get(gameID);
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
        if (gameData.getGameID() < data.size()) {
            data.set(gameData.getGameID(), gameData);
        }

        else {
            throw new DataAccessException("Game does not exist");
        }
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }

    public static MemoryGameDAO getMemoryGameDAO() {
        if (memoryGameDAO == null) {
            memoryGameDAO = new MemoryGameDAO();
        }
        return memoryGameDAO;
    }
}
