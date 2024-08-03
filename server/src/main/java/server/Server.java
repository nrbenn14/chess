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
        Spark.delete("/db", Handler.clearHandler);

        //This line initializes the server and can be removed once you have a functioning endpoint 
//        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
