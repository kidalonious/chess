package server.services;

import dataAccess.BadRequestException;
import dataAccess.UnauthorizedException;
import server.requests.JoinGameRequest;

public class JoinGameService extends Service{

    public static void joinGame(JoinGameRequest request, String authToken) throws Exception{
        if (authMemoryDAO.getAuthData(authToken) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (gameMemoryDAO.getGame(request.gameID()) == null) {
            throw new BadRequestException("bad request");
        }
        gameMemoryDAO.joinGame(request, authToken);
    }
}
