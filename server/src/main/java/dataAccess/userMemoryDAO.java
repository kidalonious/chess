package dataAccess;

import model.UserData;

import java.util.HashMap;

public class userMemoryDAO implements UserDAO {
    public HashMap<Integer, UserData> userData = new HashMap<>();
    public void clear() {
        userData.clear();
    }
}
