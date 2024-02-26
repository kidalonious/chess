package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.HashMap;
import java.util.UUID;

public class GameMemoryDAO implements GameDAO   {
    HashMap<String, GameData> gameData = new HashMap<>();
    int gameID = 0;

    public void changeID() {
        gameID++;
    }
    public void clear() {
        gameData.clear();
    }

    public void createGame(UserData whiteUser, UserData blackUser, String gameName) {
        GameData game = new GameData(gameID, whiteUser.username(), blackUser.username(), gameName, new ChessGame());
        changeID();
        gameData.put(UUID.randomUUID().toString(), game);
    }
}
