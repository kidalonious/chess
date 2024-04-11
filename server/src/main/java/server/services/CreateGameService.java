package server.services;

import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import spark.Request;

public class CreateGameService extends Service{
    public static int createGame(GameData newGame, Request request) throws Exception{
        if (newGame.gameName() == null) {
            throw new BadRequestException("bad request");
        }
        if (authDAO.getAuthData(request.headers("Authorization")) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        return gameDAO.createGame(newGame);
    }
}
