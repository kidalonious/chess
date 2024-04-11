package dataAccess;

import exceptions.DataAccessException;
import model.UserData;

public interface UserDAO {
    void clear() throws DataAccessException;
    UserData getUser(UserData user) throws DataAccessException;
    boolean userExists(UserData user);
    void createUser(String username, String password, String email) throws DataAccessException;
}
