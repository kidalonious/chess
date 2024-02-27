package server.requests;

import model.AuthData;
import model.UserData;
import spark.Request;

public class AuthRequest extends BaseRequest{
    public static AuthData createAuthData(Request request) {
        String requestString = convertToString(request);
        return gson.fromJson(requestString, AuthData.class);
    }
}
