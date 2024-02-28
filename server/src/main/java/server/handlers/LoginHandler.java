package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;
import model.AuthData;
import model.UserData;
import server.requests.UserRequest;
import server.services.LoginService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginHandler {
    public static Object handle(Request request, Response response) {
        try {
            UserData userData = UserRequest.createUserData(request);
            AuthData authData = LoginService.loginUser(userData);
            Gson gson = new Gson();
            response.status(200);
            return gson.toJson(authData);
        }
        catch (UnauthorizedException e) {
            response.status(401);
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
