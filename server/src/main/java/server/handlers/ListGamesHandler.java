package server.handlers;

import com.google.gson.Gson;
import model.GameData;
import server.requests.GameRequest;
import server.services.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ListGamesHandler {
    public static Object handle(Request request, Response response) throws Exception{
        var games = ListGamesService.listGames();
        response.status(200);
        Gson gson = new Gson();
        return gson.toJson(games);
    }
}
