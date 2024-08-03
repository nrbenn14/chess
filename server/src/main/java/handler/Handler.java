package handler;

import com.google.gson.Gson;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Route;
import spark.Request;

public class Handler {
    private static final Gson GSON = new Gson();
    private static UserService userService = new UserService();
    private static GameService gameService = new GameService();

    public static Route clearHandler = (Request request, Response response) -> {
        userService.clear();
        gameService.clear();

        response.type("application/json");
        return "{}";
    };
}
