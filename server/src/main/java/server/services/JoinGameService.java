package server.services;

import dataAccess.BadRequestException;
import dataAccess.DuplicateException;
import dataAccess.UnauthorizedException;
import server.requests.JoinGameRequest;

import java.util.Objects;

public class JoinGameService extends Service {
    public static void joinGame(JoinGameRequest request, String authToken) throws Exception {

        if (authDAO.getAuthData(authToken) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (gameDAO.getGame(request.gameID()) == null) {
            throw new BadRequestException("bad request");
        }
        if (Objects.equals(request.playerColor(), "WHITE")) {
            if (gameDAO.getGame(request.gameID()).whiteUsername() != null) {
                throw new DuplicateException("already taken");
            }
        }
        if (Objects.equals(request.playerColor(), "BLACK")) {
            if (gameDAO.getGame(request.gameID()).blackUsername() != null) {
                throw new DuplicateException("already taken");
            }
        }
        if (request.playerColor() == null) {
            return;
        }

        gameDAO.joinGame(request, authToken);
    }
}

