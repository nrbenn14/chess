package handler;

import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler extends Handler implements Route {
    public Object handle(Request request, Response response) throws DataAccessException {
        userService.clear();
        gameService.clear();

        response.type("application/json");
        return "{}";
    }
}
