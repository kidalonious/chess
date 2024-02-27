package server.handlers;

import server.services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public static Object handle(Request request, Response response) {
        String authToken = request.headers("authorization");
        LogoutService.logout(authToken);
        response.status(200);
        return "{}";
    }
}
