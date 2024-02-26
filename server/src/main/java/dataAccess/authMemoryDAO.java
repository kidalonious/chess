package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class authMemoryDAO implements AuthDAO {
    HashMap<Integer, AuthData> authData = new HashMap<>();

    public void clear() {
        authData.clear();
    }
}
