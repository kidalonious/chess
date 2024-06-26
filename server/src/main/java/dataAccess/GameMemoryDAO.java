package dataAccess;

import chess.ChessGame;
import exceptions.DataAccessException;
import model.GameData;
import requests.JoinGameRequest;

import java.util.Collection;
import java.util.HashMap;

import static server.services.Service.authDAO;

public class GameMemoryDAO //implements GameDAO   {
{
    HashMap<Integer, GameData> gameData = new HashMap<>();
    int id = 0;

    public void changeID() {
        this.id++;
    }
    public void clear() {
        gameData.clear();
    }

    public int createGame(GameData newGame) {
        newGame = new GameData(id, newGame.whiteUsername(), newGame.blackUsername(), newGame.gameName(), newGame.game());
        gameData.put(id, newGame);
        changeID();
        return newGame.gameID();
    }

    public GameData getGame(int gameID) {
        return gameData.get(gameID);
    }
    public void updateGame(GameData gameData) {
        ChessGame newGame = gameData.game();
    }

    public Collection<GameData> listGames() {
        return gameData.values();
    }

    public void joinGame(JoinGameRequest request, String authToken) throws DataAccessException {
        if (request.playerColor().equals("BLACK")) {
            String currentWhiteUsername = gameData.get(request.gameID()).whiteUsername();
            String currentGameName = gameData.get(request.gameID()).gameName();
            ChessGame currentGame = gameData.get(request.gameID()).game();
            String newBlackUser = authDAO.getAuthData(authToken).username();
            GameData copiedGame = new GameData(request.gameID(), currentWhiteUsername, newBlackUser, currentGameName, currentGame);
            gameData.replace(request.gameID(), copiedGame);
        }
        else if (request.playerColor().equals("WHITE")) {
            String currentBlackUsername = gameData.get(request.gameID()).blackUsername();
            String currentGameName = gameData.get(request.gameID()).gameName();
            ChessGame currentGame = gameData.get(request.gameID()).game();
            String newWhiteUser = authDAO.getAuthData(authToken).username();
            GameData copiedGame = new GameData(request.gameID(), newWhiteUser, currentBlackUsername, currentGameName, currentGame);
            gameData.replace(request.gameID(), copiedGame);
        }
    }
}
