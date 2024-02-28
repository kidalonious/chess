package server.handlers;

import dataAccess.DataAccessException;
import server.services.ClearService;
import spark.*;

import javax.xml.crypto.Data;

public class ClearHandler {
    public static Object handle(Request request, Response response) {
        ClearService.clear();
        response.status(200);
        return "";
    }
}
