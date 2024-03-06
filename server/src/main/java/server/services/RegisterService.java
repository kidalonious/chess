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
        if (userDAO.getUser(userData) != null) {
            throw new DuplicateException("already taken");
        }
        userDAO.createUser(userData.username(), userData.password(), userData.email());
        return authDAO.addAuthData(userData);
    }
}
