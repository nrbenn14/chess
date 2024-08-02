package server;

import dataaccess.*;
import service.AuthService;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    private final UserService userService;
    UserDAO memoryUserDAO = new MemoryUserDAO();
    private final GameService gameService;
    GameDAO memoryGameDAO = new MemoryGameDAO();
    private final AuthService authService;
    AuthDAO memoryAuthDAO = new MemoryAuthDAO();

    public Server() {
        userService = new UserService(memoryUserDAO);
        gameService = new GameService(memoryGameDAO);
        authService = new AuthService(memoryAuthDAO);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        //This line initializes the server and can be removed once you have a functioning endpoint 
//        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object clear(Request request, Response response) {
        userService.clear();
        gameService.clear();
        authService.clear();

        return "{}";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
