package handler;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Route;
import spark.Request;

import java.util.ArrayList;

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

    public static Route clearHandler = (Request request, Response response) -> {
        userService.clear();
        gameService.clear();

        response.type("application/json");
        return "{}";
    };


}
