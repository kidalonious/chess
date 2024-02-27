package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameMemoryDAO implements GameDAO   {
    HashMap<Integer, GameData> gameData = new HashMap<>();
    int gameID = 0;

    public void changeID() {
        gameID++;
    }
    public void clear() {
        gameData.clear();
    }

    public static int createGame(GameData newGame) {
        gameData.put(gameID, newGame);
        changeID();
        return gameID-1;
    }

    public void updateGame(int gameID) {

    }

    public Collection<GameData> listGames() {
        ArrayList<GameData> games = new ArrayList<>();
        for (Integer game : gameData.keySet()) {
            games.add(gameData.get(game));
        }
        return games;
    }
}
