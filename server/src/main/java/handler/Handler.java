package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Route;
import spark.Request;

import java.util.ArrayList;
import java.util.Objects;

public class Handler {
    private static final Gson GSON = new Gson();
    private static UserService userService = new UserService();
    private static GameService gameService = new GameService();

    public static class GamesList {
        private ArrayList<GameData> gamesList;

        public GamesList(ArrayList<GameData> games) {
            this.gamesList = games;
        }
    }

    public static Route registerHandler = (Request request, Response response) -> {
        UserData userData = GSON.fromJson(request.body(), UserData.class);
        AuthData authData = userService.register(userData);

        response.type("application/json");
        return GSON.toJson(authData);
    };

    public static Route loginHandler = (Request request, Response response) -> {
        UserData userData = GSON.fromJson(request.body(), UserData.class);
        AuthData authData = userService.login(userData);

        response.type("application/json");
        return GSON.toJson(authData);
    };

    public static Route logoutHandler = (Request request, Response response) -> {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setAuthToken(authToken);
        authData.setUsername(null);
        userService.logout(authData);

        response.type("application/json");
        return GSON.toJson(authData);
    };

    public static Route listGamesHandler = (Request request, Response response) -> {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setUsername(null);
        authData.setAuthToken(authToken);

        ArrayList<GameData> games = new ArrayList<>();
        for (GameData gameData : gameService.listGames(authData)) {
            games.add(new GameData(gameData.getGameID() + 1, gameData.getWhiteUsername(), gameData.getBlackUsername(), gameData.getGameName(), gameData.getGame()));
        }

        GamesList gamesList = new GamesList(games);

        response.type("application/json");
        return GSON.toJson(gamesList);
    };

    public static Route createGameHandler = (Request request, Response response) -> {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setAuthToken(authToken);
        authData.setUsername(null);

        GameData gameName = GSON.fromJson(request.body(), GameData.class);

        GameData game = gameService.createGame(authData, gameName);
        game = new GameData(game.getGameID() + 1, game.getWhiteUsername(), game.getBlackUsername(), game.getGameName(), game.getGame());

        response.type("application/json");
        return GSON.toJson(game);
    };

    public static Route clearHandler = (Request request, Response response) -> {
        userService.clear();
        gameService.clear();

        response.type("application/json");
        return "{}";
    };

    public static Route joinGameHandler = (Request request, Response response) -> {
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
    };


}
