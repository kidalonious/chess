package server.services;

import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import dataAccess.UnauthorizedException;
import server.requests.JoinGameRequest;

import java.util.Objects;

public class JoinGameService extends Service{
    public static void joinGame(JoinGameRequest request, String authToken){
        gameMemoryDAO.joinGame(request, authToken);
    }

}

