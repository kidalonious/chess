package server.handlers;

import server.services.RegisterService;
import spark.*;

public class RegisterHandler {
    public static Object handle(Request request, Response response) {

        RegisterService.register();
        return "";
    }
}
