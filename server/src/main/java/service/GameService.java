package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;

import java.util.ArrayList;

public class GameService extends Service {

    public ArrayList<GameData> listGames(AuthData authData) throws DataAccessException {
        AuthData authData1 = authDAO.readAuth(authData.getAuthToken());
        String user = null;
        if (authData1 != null) {
            user = authData1.getAuthToken();
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
        return gameDAO.listGames();
    }

    public void clear() {
        gameDAO.clear();
    }
}
