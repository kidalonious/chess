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
        userData.put(user.username(), user);
    }

    private boolean userExists(UserData user) throws DataAccessException{
        if (userData.containsKey(user.username())) {
            return true;
        }
        throw new DataAccessException("");
    }

    public UserData checkPassword(UserData user) throws DataAccessException {
        if (userExists(user)) {
            if (userData.get(user.username()).password().equals(user.password())) {
                return user;
            }
        }
        throw new DataAccessException("");
    }
}
