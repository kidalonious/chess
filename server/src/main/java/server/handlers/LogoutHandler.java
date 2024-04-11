package server.handlers;

import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.UnauthorizedException;
import server.services.LogoutService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LogoutHandler {
    public static Object handle(Request request, Response response) {
        String authToken = request.headers("Authorization");
        try {
            LogoutService.logout(authToken);
            response.status(200);
            return "{}";
        }
        catch (UnauthorizedException e) {
            response.status(401);
            Map<String, String> attributeMap = new HashMap<>();
            attributeMap.put("message", "Error: unauthorized");
            return new Gson().toJson(attributeMap);
        }
        catch (DataAccessException e) {
            response.status(500);
            Map<String, String> attributeMap = new HashMap<>();
            attributeMap.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(attributeMap);
        }
    }
}
