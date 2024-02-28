package server.services;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.UnauthorizedException;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class LogoutService extends Service {
    public static void logout(String auth) throws UnauthorizedException, DataAccessException {
        if (auth == null) {
            throw new UnauthorizedException("unauthorized");
        }
        authMemoryDAO.deleteAuth(auth);
    }
}
