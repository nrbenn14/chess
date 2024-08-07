package server;

import com.google.gson.Gson;
import dataaccess.*;
import handler.Handler;
import handler.RegisterHandler;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {

    private static final Gson GSON = new Gson();

//    RegisterHandler registerHandler = new RegisterHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);


        Spark.staticFiles.location("web");
        RegisterHandler registerHandler = new RegisterHandler();

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", registerHandler::handle);
        Spark.post("/session", Handler.loginHandler);
        Spark.delete("/session", Handler.logoutHandler);
        Spark.get("/game", Handler.listGamesHandler);
        Spark.post("/game", Handler.createGameHandler);
        Spark.put("/game", Handler.joinGameHandler);
        Spark.delete("/db", Handler.clearHandler);

        Spark.exception(Exception.class, (e, request, response) -> {
            String message = e.getMessage();
            switch (message) {
                case "Error: Username/password required", "Error: game name required",
                        "Error: invalid team color", "Error: invalid game ID" -> response.status(400);
                case "Error: Username and/or password were incorrect", "Error: unauthorized" -> response.status(401);
                case "Error: Username already exists", "Error: team already taken" -> response.status(403);
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
