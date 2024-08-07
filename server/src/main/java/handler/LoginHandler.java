package handler;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;
import spark.Route;


public class LoginHandler extends Handler implements Route {
    public Object handle(Request request, Response response) throws DataAccessException {
        UserData userData = GSON.fromJson(request.body(), UserData.class);
        AuthData authData = userService.login(userData);

        response.type("application/json");
        return GSON.toJson(authData);
    }
}
