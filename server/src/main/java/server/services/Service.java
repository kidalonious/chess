package server.services;

import dataAccess.AuthMemoryDAO;
import dataAccess.GameMemoryDAO;
import dataAccess.UserMemoryDAO;

public class Service {
    public static AuthMemoryDAO authMemoryDAO = new AuthMemoryDAO();
    public static GameMemoryDAO gameMemoryDAO = new GameMemoryDAO();
    public static UserMemoryDAO userMemoryDAO = new UserMemoryDAO();
}
