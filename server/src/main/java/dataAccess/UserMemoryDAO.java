package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class UserMemoryDAO implements UserDAO {
    public HashMap<UUID, UserData> userData = new HashMap<>();
    public void clear() {
        userData.clear();
    }

    public UserData createUser(UserData user) {
        userData.put(UUID.randomUUID(), user);
        return user;
    }
}
