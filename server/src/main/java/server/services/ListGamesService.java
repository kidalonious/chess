package server.services;

import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;
import model.GameData;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class ListGamesService extends Service{
    public static Collection<GameData> listGames(Request request, Response response) throws Exception{
        if (request.headers("Authorization") == null) {
            response.status(401);
            throw new UnauthorizedException("unauthorized");
        }
        return gameMemoryDAO.listGames();
    }
}
