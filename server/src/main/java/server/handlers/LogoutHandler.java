package server.handlers;

import server.services.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler {
    public static Object handle(Request request, Response response) {
        //LogoutService.logout(user);
        response.status(200);
        return "";
    }
}
