package server.handlers;

import com.google.gson.Gson;
import model.GameData;
import server.requests.GameRequest;
import server.services.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler {
    public static Object handle(Request request, Response response) {
        String usableRequest = GameRequest.convertToString(request);
        GameData newGame = GameRequest.gson.fromJson(usableRequest, GameData.class);
        int gameID = CreateGameService.createGame(newGame);
        response.status(200);
        Gson gson = new Gson();

        return gson.toJson(newGame);
    }
}
