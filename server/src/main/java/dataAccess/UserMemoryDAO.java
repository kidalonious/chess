package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class UserMemoryDAO implements UserDAO {
    public HashMap<String, UserData> userData = new HashMap<>();
    public void clear() {
        userData.clear();
    }

    public void createUser(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        userData.put(UUID.randomUUID().toString(), user);
    }
}
