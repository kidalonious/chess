package dataAccess;

import exceptions.DataAccessException;
import model.AuthData;
import model.UserData;

public interface AuthDAO {
    void clear() throws DataAccessException;

    AuthData getAuthData(String authToken) throws DataAccessException;
    AuthData addAuthData(UserData userData) throws DataAccessException;
    String generateAuthToken();
    void deleteAuth(String auth) throws DataAccessException;

}
