package dataAccess;

import exceptions.DataAccessException;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    void changeID();
    int createGame(GameData newGame) throws DataAccessException;
    GameData getGame(int gameID) throws Exception;
    Collection<GameData> listGames() throws Exception;
    void joinGame(JoinGameRequest request, String authToken) throws DataAccessException;

    void updateGame(GameData gameData) throws Exception;
}
