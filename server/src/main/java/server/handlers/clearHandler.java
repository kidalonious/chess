package server.handlers;

import server.services.ClearService;
import spark.*;

public class clearHandler {
    public static Object handle(Request request, Response response) {
        ClearService clearService = new ClearService();
        clearService.clear();
        response.status(200);
        return "";
    }
}
