package handler;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Route;
import spark.Request;

public class Handler {
    private static final Gson GSON = new Gson();
    private static UserService userService = new UserService();
    private static GameService gameService = new GameService();


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

    public static Route clearHandler = (Request request, Response response) -> {
        userService.clear();
        gameService.clear();

        response.type("application/json");
        return "{}";
    };
}
