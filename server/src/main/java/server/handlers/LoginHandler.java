package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import server.requests.UserRequest;
import server.services.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public static Object handle(Request request, Response response) throws DataAccessException {
        UserData userData = UserRequest.createUserData(request);
        AuthData authData = LoginService.loginUser(userData);
        Gson gson = new Gson();
        response.status(200);
        return gson.toJson(authData);
    }
}
