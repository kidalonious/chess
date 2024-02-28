package server.services;

import dataAccess.BadRequestException;
import dataAccess.DuplicateException;
import model.AuthData;
import model.UserData;

public class RegisterService extends Service {
    public static AuthData register(UserData userData) throws Exception{
        if (userData.username() == null || userData.password() == null || userData.email() == null) {
            throw new BadRequestException("bad request");
        }
        if (userMemoryDAO.userExists(userData)) {
            throw new DuplicateException("already taken");
        }
        userMemoryDAO.createUser(userData.username(), userData.password(), userData.email());
        return authMemoryDAO.addAuthData(userData);
    }
}
