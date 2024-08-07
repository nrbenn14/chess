package handler;

import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler extends Handler implements Route {
    public Object handle(Request request, Response response) throws DataAccessException {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setAuthToken(authToken);
        authData.setUsername(null);

        GameData gameName = GSON.fromJson(request.body(), GameData.class);

        GameData game = gameService.createGame(authData, gameName);
        game = new GameData(game.getGameID() + 1, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(), game.getGame());

        response.type("application/json");
        return GSON.toJson(game);
    }
}
