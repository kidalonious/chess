package server.services;

import dataAccess.DataAccessException;

public class ClearService extends Service{
    public static void clear() throws DataAccessException {
        userDAO.clear();
        gameDAO.clear();
        authDAO.clear();
    }
}
