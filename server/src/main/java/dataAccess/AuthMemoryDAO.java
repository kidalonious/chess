package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class AuthMemoryDAO implements AuthDAO {
    HashMap<String, AuthData> authData = new HashMap<>();

    public void clear() {
        authData.clear();
    }

    private String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
    public AuthData addAuthData(UserData userData) {
        AuthData auth = new AuthData(userData.username(), generateAuthToken());
        authData.put(auth.authToken(), auth);
        return auth;
    }

    public AuthData getAuthData(String authToken) {
        return authData.get(authToken);
    }
    public void deleteAuth(String auth) {
        AuthData desiredAuth = getAuthData(auth);
        if (desiredAuth != null) {
            authData.remove(auth);
        }
    }
}
