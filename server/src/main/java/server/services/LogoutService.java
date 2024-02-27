package server.services;

import model.AuthData;

public class LogoutService extends Service {
    public static void logout(String auth) {
        authMemoryDAO.deleteAuth(auth);
    }
}
