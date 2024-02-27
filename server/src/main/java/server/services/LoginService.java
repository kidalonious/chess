package server.services;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

public class LoginService extends Service{
    public static AuthData loginUser(UserData user) throws DataAccessException {
        userMemoryDAO.checkPassword(user);
        return authMemoryDAO.addAuthData(user);
    }
}
