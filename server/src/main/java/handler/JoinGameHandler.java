package handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class JoinGameHandler extends Handler implements Route {
    public Object handle(Request request, Response response) throws DataAccessException {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setUsername(null);
        authData.setAuthToken(authToken);

        GameData gameToJoin = GSON.fromJson(request.body(), GameData.class);

        JsonObject jsonObject = JsonParser.parseString(request.body()).getAsJsonObject();
        String teamColor = null;

        if (jsonObject.has("playerColor")) {
            teamColor = jsonObject.get("playerColor").getAsString();
        }

        if (Objects.equals(teamColor, "BLACK")) {
            gameToJoin = new GameData(gameToJoin.getGameID(), null, teamColor, null, null);
        }
        else if (Objects.equals(teamColor, "WHITE")) {
            gameToJoin = new GameData(gameToJoin.getGameID(), teamColor, null, null, null);
        }
        else {
            throw new DataAccessException("Error: invalid team color");
        }

        gameToJoin = new GameData(gameToJoin.getGameID() - 1, gameToJoin.getWhiteUsername(), gameToJoin.getBlackUsername(), null, null);
        gameService.joinGame(authData, gameToJoin);

        response.type("application/json");
        return "{}";
    }
}
