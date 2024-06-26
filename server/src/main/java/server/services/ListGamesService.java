package server.services;

import exceptions.UnauthorizedException;
import model.GameData;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class ListGamesService extends Service{
    public static Collection<GameData> listGames(Request request, Response response) throws Exception {
        if (authDAO.getAuthData(request.headers("Authorization")) == null) {
            response.status(401);
            throw new UnauthorizedException("unauthorized");
        }
        return gameDAO.listGames();
    }
}
