package dataAccess;

import model.AuthData;
import model.UserData;
import org.springframework.security.core.userdetails.User;

public interface AuthDAO {
    void clear();

    AuthData getAuthData(String authToken);
    AuthData addAuthData(UserData userData);
    String generateAuthToken();
    void deleteAuth(String auth);

}
