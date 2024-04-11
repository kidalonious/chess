package dataAccess;

import exceptions.DataAccessException;
import model.UserData;

import java.util.HashMap;

public class UserMemoryDAO implements UserDAO {
    public HashMap<String, UserData> userData = new HashMap<>();
    public void clear() {
        userData.clear();
    }

    public void createUser(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        userData.put(user.username(), user);
    }

    public boolean userExists(UserData user){
        return userData.containsKey(user.username());
    }

    public UserData getUser(UserData user) {
        return userData.get(user.username());
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
