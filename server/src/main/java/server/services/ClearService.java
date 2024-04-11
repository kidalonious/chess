package server.services;

import exceptions.DataAccessException;

public class ClearService extends Service{
    public static void clear() throws DataAccessException {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
    }
}
