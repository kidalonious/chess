package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
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
            if (request.headers("Authorization") == null) {
                response.status(401);
                throw new DataAccessException("unauthorized");
            }
            Collection<GameData> games = ListGamesService.listGames();
            response.status(200);
            Gson gson = new GsonBuilder().serializeNulls().create();
            Map<String, Collection<GameData>> attributeMap = new HashMap<>();
            attributeMap.put("games", games);
            return gson.toJson(attributeMap);
        }
        catch (DataAccessException e) {
            String message = e.getMessage();
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + message);
            return new Gson().toJson(errorMessage);
        }
    }
}
