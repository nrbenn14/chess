package handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import service.GameService;
import service.UserService;
import spark.Response;
import spark.Route;
import spark.Request;

import java.util.ArrayList;
import java.util.Objects;

public class Handler {
    static final Gson GSON = new Gson();
    static UserService userService = new UserService();
    static GameService gameService = new GameService();
}
