package dataAccess;

import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void clear();
    UserData getUser(UserData user);
    boolean userExists(UserData user);
    void createUser(String username, String password, String email);
}
