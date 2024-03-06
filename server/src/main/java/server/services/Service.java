package server.services;

import dataAccess.*;

public class Service {
    public static AuthDAO authDAO;

    static {
        try {
            authDAO = new SQLAuthDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static GameDAO gameDAO;

    static {
        try {
            gameDAO = new SQLGameDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UserDAO userDAO;

    static {
        try {
            userDAO = new SQLUserDAO();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
