package server.requests;

import model.UserData;
import spark.Request;

public class UserRequest extends BaseRequest {
    public static UserData createUserData(Request request) {
        String requestString = convertToString(request);
        return gson.fromJson(requestString, UserData.class);
    }
}
