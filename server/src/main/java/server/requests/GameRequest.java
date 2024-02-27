package server.requests;

import model.GameData;
import model.UserData;
import spark.Request;

public class GameRequest extends BaseRequest{
    public static GameData createGameData(Request request) {
        String requestString = convertToString(request);
        return gson.fromJson(requestString, GameData.class);
    }
}
