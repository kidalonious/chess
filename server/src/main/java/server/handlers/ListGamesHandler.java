package server.handlers;

import server.services.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler {
    public static Object handle(Request request, Response response) {

        ListGamesService.listGames();
        return "";
    }
}
