package dataAccess;

import model.GameData;
import server.requests.JoinGameRequest;

import java.util.Collection;

public interface GameDAO {
    void clear() throws DataAccessException;
    void changeID();
    int createGame(GameData newGame) throws DataAccessException;
    GameData getGame(int gameID);
    Collection<GameData> listGames();
    void joinGame(JoinGameRequest request, String authToken);
}
