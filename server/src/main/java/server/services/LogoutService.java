package server.services;

import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;

public class LogoutService extends Service {
    public static void logout(String auth) throws UnauthorizedException, DataAccessException {
        if (authDAO.getAuthData(auth) == null) {
            throw new UnauthorizedException("unauthorized");
        }
        authDAO.deleteAuth(auth);
    }
}
