package server.services;

import dataAccess.GameMemoryDAO;
import model.GameData;

public class CreateGameService extends Service{
    public static int createGame(GameData newGame) {
        return gameMemoryDAO.createGame(newGame);
    }
}
