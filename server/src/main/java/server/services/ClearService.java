package server.services;

public class ClearService extends Service{
    public static void clear() {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
    }
}
