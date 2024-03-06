package server.services;

import dataAccess.*;

public class Service {
    public static AuthDAO authDAO = new AuthMemoryDAO();
    public static GameDAO gameDAO = new GameMemoryDAO();
    public static UserDAO userDAO = new UserMemoryDAO();
}
