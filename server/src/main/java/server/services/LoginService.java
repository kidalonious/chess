package server.services;

import exceptions.UnauthorizedException;
import model.AuthData;
import model.UserData;

import java.util.Objects;

public class LoginService extends Service{
    public static AuthData loginUser(UserData user) throws Exception {
        UserData newUser = userDAO.getUser(user);
        if (newUser == null) {
            throw new UnauthorizedException("unauthorized");
        }
        if (!Objects.equals(newUser.password(), user.password())) {
            throw new UnauthorizedException("unauthorized");
        }
        //userDAO.checkPassword(user);
        return authDAO.addAuthData(user);
    }
}
