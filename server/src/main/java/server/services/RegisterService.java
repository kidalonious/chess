package server.services;

import model.AuthData;
import model.UserData;

public class RegisterService extends Service {
    public static AuthData register(UserData userData) {
        userMemoryDAO.createUser(userData.username(), userData.password(), userData.email());
        return authMemoryDAO.addAuthData(userData);
    }
}
