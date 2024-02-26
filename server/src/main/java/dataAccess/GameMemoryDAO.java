package dataAccess;

import model.GameData;

import java.util.HashMap;

public class GameMemoryDAO implements GameDAO   {
    HashMap<Integer, GameData> gameData = new HashMap<>();

    public void clear() {
        gameData.clear();
    }
}
