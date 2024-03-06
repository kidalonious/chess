package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import server.services.ClearService;
import spark.*;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class ClearHandler {
    public static Object handle(Request request, Response response) {
        try {
            ClearService.clear();
            response.status(200);
            return "";
        }
        catch (Exception e) {
            response.status(500);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
    }
}
