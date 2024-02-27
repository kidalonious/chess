package server.services;

import model.AuthData;

public class LogoutService extends Service {
    public static void logout(AuthData auth) {
        authMemoryDAO.deleteAuth(auth);
    }
}
