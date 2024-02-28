package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;
import model.GameData;
import server.requests.GameRequest;
import server.services.ListGamesService;
import spark.Request;
import spark.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ListGamesHandler {
    public static Object handle(Request request, Response response) {
        try {
            Collection<GameData> games = ListGamesService.listGames(request, response);
            response.status(200);
            Gson gson = new GsonBuilder().serializeNulls().create();
            Map<String, Collection<GameData>> attributeMap = new HashMap<>();
            attributeMap.put("games", games);
            return gson.toJson(attributeMap);
        }
        catch (UnauthorizedException e) {
            response.status(401);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
        catch (Exception e) {
            response.status(500);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
    }
}
