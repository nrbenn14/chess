package dataaccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO extends GameDAO {
    public Collection<GameData> gameData;

    public MemoryGameDAO() {
        gameData = new ArrayList<GameData>();
    }

    @Override
    public void createGame() {
    }

    @Override
    public void readGame() {
    }

    @Override
    public void updateGame() {
    }

    @Override
    public void deleteGame() {
    }

    @Override
    public void clear() {
        gameData.clear();
    }
}
