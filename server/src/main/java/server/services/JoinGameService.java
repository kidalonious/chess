package server.services;

import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import dataAccess.UnauthorizedException;
import server.requests.JoinGameRequest;

import java.util.Objects;

public class JoinGameService extends Service{
    public static void joinGame(JoinGameRequest request, String authToken) throws UnauthorizedException, BadRequestException, DuplicateException, DataAccessException{
        if (authMemoryDAO.getAuthData(authToken) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (gameMemoryDAO.getGame(request.gameID()) == null) {
            throw new BadRequestException("bad request");
        }
        if ((request.playerColor().equals("WHITE") && gameMemoryDAO.getGame(request.gameID()).whiteUsername() != null)
                || (request.playerColor().equals("BLACK") && gameMemoryDAO.getGame(request.gameID()).blackUsername() != null)) {
            throw new DuplicateException("already taken");
        }
        gameMemoryDAO.joinGame(request, authToken);
    }

}

