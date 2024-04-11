package server.services;

import exceptions.DataAccessException;
import exceptions.UnauthorizedException;

public class LogoutService extends Service {
    public static void logout(String auth) throws UnauthorizedException, DataAccessException {
        if (authDAO.getAuthData(auth) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        authDAO.deleteAuth(auth);
    }
}
