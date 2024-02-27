package server.handlers;

import com.google.gson.Gson;
import server.services.LogoutService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LogoutHandler {
    public static Object handle(Request request, Response response) {
        String authToken = request.headers("Authorization");
        String userName = request.headers().toString();
        System.out.println(userName);
        if (authToken == null) {
            response.status(401);
            Map<String, String> attributeMap = new HashMap<>();
            attributeMap.put("message", "Error: unauthorized");
            return new Gson().toJson(attributeMap);
        }
        LogoutService.logout(authToken);
        response.status(200);
        return "{}";
    }
}
