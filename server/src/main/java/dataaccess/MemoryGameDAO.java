package dataaccess;

import model.GameData;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDAO {
    private ArrayList<GameData> data;
    private static MemoryGameDAO memoryGameDAO;

    public MemoryGameDAO() {
        data = new ArrayList<>();
    }

    @Override
    public boolean createGame(GameData gameData) {
        gameData.setGameID(data.size());
        data.add(gameData);
        return true;
    }

    @Override
    public GameData readGame(int gameID) throws DataAccessException {
        if (gameID >= data.size() || gameID < 0) {
            throw new DataAccessException("Error: invalid game ID");
        }

        return data.get(gameID);
    }

    @Override
    public void updateGame(GameData gameData) throws DataAccessException {
        if (gameData.getGameID() < data.size() && gameData.getGameID() >= 0) {
            data.set(gameData.getGameID(), gameData);
        }

        else {
            throw new DataAccessException("Error: Game does not exist");
        }
    }

    @Override
    public ArrayList<GameData> listGames() {
        return new ArrayList<>(data);
    }

    @Override
    public void clear() {
        data.clear();
    }

    public static MemoryGameDAO getMemoryGameDAO() {
        if (memoryGameDAO == null) {
            memoryGameDAO = new MemoryGameDAO();
        }
        return memoryGameDAO;
    }
}
