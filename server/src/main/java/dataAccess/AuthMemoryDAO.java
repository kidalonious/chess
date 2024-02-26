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

    public String generateAuthToken(UserData userData) {
        return UUID.randomUUID().toString();
    }
    public void addAuthData(String authToken, UserData userData) {
        AuthData auth = new AuthData(authToken, userData.username());
        authData.put(generateAuthToken(userData), auth);
    }
}
