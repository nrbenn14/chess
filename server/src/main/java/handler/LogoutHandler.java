package handler;

import dataaccess.DataAccessException;
import model.AuthData;
import spark.Request;
import spark.Response;
import spark.Route;

import static handler.Handler.userService;

public class LogoutHandler extends Handler implements Route {
    public Object handle(Request request, Response response) throws DataAccessException {
        String authToken = request.headers("authorization");
        AuthData authData = new AuthData();
        authData.setAuthToken(authToken);
        authData.setUsername(null);
        userService.logout(authData);

        response.type("application/json");
        return "{}";
    }
}
