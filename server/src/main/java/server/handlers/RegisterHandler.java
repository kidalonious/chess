package server.handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import dataAccess.DuplicateException;
import model.AuthData;
import model.UserData;
import server.requests.UserRequest;
import server.services.RegisterService;
import spark.*;

import java.util.HashMap;
import java.util.Map;

public class RegisterHandler {
    public static Object handle(Request request, Response response) {
        try {
            UserData userData = UserRequest.createUserData(request);
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
            response.status(500);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }
    }
}
