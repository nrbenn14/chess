package server;

import com.google.gson.Gson;
import dataaccess.*;
import handler.Handler;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    private static final Gson GSON = new Gson();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", Handler.registerHandler);
        Spark.post("/session", Handler.loginHandler);
        Spark.delete("/session", Handler.logoutHandler);
        Spark.get("/game", Handler.listGamesHandler);
        Spark.delete("/db", Handler.clearHandler);

        Spark.exception(Exception.class, (e, request, response) -> {
            String message = e.getMessage();
            switch (message) {
                case "Error: Username/password required" -> response.status(400);
                case "Error: Username and/or password were incorrect", "Error: unauthorized" -> response.status(401);
                case "Error: Username already exists" -> response.status(403);
                default -> response.status(500);
            }

            response.type("application/json");
            response.body(GSON.toJson(new ErrorResponse(message)));
        });

        //This line initializes the server and can be removed once you have a functioning endpoint 
//        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
