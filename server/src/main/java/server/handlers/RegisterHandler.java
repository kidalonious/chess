package server.handlers;

import com.google.gson.Gson;
import exceptions.BadRequestException;
import exceptions.DuplicateException;
import model.AuthData;
import model.UserData;
import server.services.RegisterService;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class RegisterHandler {
    public static Object handle(Request request, Response response) {
        try {
            UserData userData = new Gson().fromJson(request.body(), UserData.class);
            AuthData authData = RegisterService.register(userData);
            response.status(200);
            Gson gson = new Gson();
            return gson.toJson(authData);
        }
        catch (BadRequestException e) {
            response.status(400);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
        catch (DuplicateException e) {
            response.status(403);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
    }
}
