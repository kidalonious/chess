package server.services;

import dataAccess.BadRequestException;
import dataAccess.GameMemoryDAO;
import dataAccess.UnauthorizedException;
import model.GameData;
import spark.Request;

public class CreateGameService extends Service{
    public static int createGame(GameData newGame, Request request) throws Exception{
        if (newGame.gameName() == null) {
            throw new BadRequestException("bad request");
        }
        if (authMemoryDAO.getAuthData(request.headers("Authorization")) == null) {
            throw new UnauthorizedException("unauthorized");
        }

        return gameMemoryDAO.createGame(newGame);
    }
}
