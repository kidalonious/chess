package server.handlers;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import requests.GameRequest;
import server.services.CreateGameService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class CreateGameHandler {
    public static Object handle(Request request, Response response) {
        try {
            String usableRequest = GameRequest.convertToString(request);
            GameData newGame = GameRequest.gson.fromJson(usableRequest, GameData.class);
            int gameID = CreateGameService.createGame(newGame, request);
            response.status(200);
            Map<String, Integer> attributeMap = new HashMap<>();
            attributeMap.put("gameID", gameID);
            Gson gson = new Gson();
            return gson.toJson(attributeMap);
        }
        catch (UnauthorizedException e) {
            response.status(401);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
        catch (BadRequestException e) {
            response.status(400);
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
