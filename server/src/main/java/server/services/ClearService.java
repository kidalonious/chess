package server.services;

public class ClearService extends Service{
    public static void clear() {
        userMemoryDAO.clear();
        gameMemoryDAO.clear();
        authMemoryDAO.clear();
    }
}
