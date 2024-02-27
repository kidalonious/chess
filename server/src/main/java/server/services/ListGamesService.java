package server.services;

import model.GameData;

import java.util.Collection;

public class ListGamesService extends Service{
    public static Collection<GameData> listGames() {
        return gameMemoryDAO.listGames();
    }
}
