package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GameMemoryDAO implements GameDAO   {
    HashMap<Integer, GameData> gameData = new HashMap<>();
    int id = 0;

    private void changeID() {
        this.id++;
    }
    public void clear() {
        gameData.clear();
    }

    public int createGame(GameData newGame) {
        newGame = new GameData(id++, newGame.whiteUsername(), newGame.blackUsername(), newGame.gameName(), newGame.game());
        gameData.put(id, newGame);
        changeID();
        return newGame.gameID();
    }

    public GameData getGame(int gameID) {
        return gameData.get(gameID);
    }
    public void updateGame(int gameID) {

    }

    public Collection<GameData> listGames() {
        return gameData.values();
    }
}
