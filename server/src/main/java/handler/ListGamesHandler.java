package handler;

import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;

public class ListGamesHandler extends Handler implements Route {
    public Object handle(Request request, Response response) throws DataAccessException {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setUsername(null);
        authData.setAuthToken(authToken);

        ArrayList<GameData> gamesList = new ArrayList<>();
        for (GameData gameData : gameService.listGames(authData)) {
            gamesList.add(new GameData(gameData.getGameID() + 1, gameData.getWhiteUsername(), gameData.getBlackUsername(),
                    gameData.getGameName(), gameData.getGame()));
        }

        GamesList games = new GamesList(gamesList);

        response.type("application/json");
        return GSON.toJson(games);
    }
}
