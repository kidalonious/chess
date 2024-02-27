package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
        Collection<GameData> games = ListGamesService.listGames();
        response.status(200);
        Map<String, Collection<GameData>> attributeMap = new HashMap<>();
        attributeMap.put("games", games);
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(attributeMap);
    }
}
