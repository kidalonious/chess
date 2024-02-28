package server.services;

import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import dataAccess.UnauthorizedException;
import server.requests.JoinGameRequest;

import java.util.Objects;

public class JoinGameService extends Service {
    public static void joinGame(JoinGameRequest request, String authToken) throws Exception {

        if (authMemoryDAO.getAuthData(authToken) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (gameMemoryDAO.getGame(request.gameID()) == null) {
            throw new BadRequestException("bad request");
        }
        if (Objects.equals(request.playerColor(), "WHITE")) {
            if (gameMemoryDAO.getGame(request.gameID()).whiteUsername() != null) {
                throw new DuplicateException("already taken");
            }
        }
        if (Objects.equals(request.playerColor(), "BLACK")) {
            if (gameMemoryDAO.getGame(request.gameID()).blackUsername() != null) {
                throw new DuplicateException("already taken");
            }
        }
        if (request.playerColor() == null) {
            return;
        }

        gameMemoryDAO.joinGame(request, authToken);
    }
}

