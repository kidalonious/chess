package server.handlers;

import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
import server.requests.UserRequest;
import server.services.RegisterService;
import spark.*;

public class RegisterHandler {
    public static Object handle(Request request, Response response) {
        UserData userData = UserRequest.createUserData(request);
        AuthData authData = RegisterService.register(userData);
        response.status(200);
        Gson gson = new Gson();
        return gson.toJson(authData);
    }
}
