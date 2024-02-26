package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class AuthMemoryDAO implements AuthDAO {
    HashMap<UUID, AuthData> authData = new HashMap<>();

    public void clear() {
        authData.clear();
    }

    public void generateAuthToken(UserData userData) {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, userData.username());
        authData.put(UUID.randomUUID(), auth);
    }
}
