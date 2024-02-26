package server.handlers;

import server.services.ClearService;
import spark.*;

public class ClearHandler {
    public static Object handle(Request request, Response response) throws Exception {
        //ClearService clearService = new ClearService();
        ClearService.clear();
        response.status(200);
        return "";
    }
}
