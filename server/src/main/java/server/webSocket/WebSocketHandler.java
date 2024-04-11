package server.webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.ResponseException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.services.Service;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.util.Objects;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER: {
                JoinPlayer command = new Gson().fromJson(message, JoinPlayer.class);
                joinPlayer(command, session);
                break;
            }
            case LEAVE: {
                Leave command = new Gson().fromJson(message, Leave.class);
                leave(command);
                break;
            }
            case RESIGN: {
                Resign command = new Gson().fromJson(message, Resign.class);
                resign(command);
                break;
            }
            case MAKE_MOVE: {
                MakeMove command = new Gson().fromJson(message, MakeMove.class);
                makeMove(command);
                break;
            }
            case JOIN_OBSERVER: {
                JoinObserver command = new Gson().fromJson(message, JoinObserver.class);
                joinObserver(command, session);
                break;
            }
        }
    }

    public void joinPlayer(JoinPlayer command, Session session) throws Exception {
        String authToken = command.getAuthString();
        GameData gameData = Service.gameDAO.getGame(command.gameID);
        ChessGame.TeamColor playerColor = command.playerColor;
        AuthData authData = Service.authDAO.getAuthData(authToken);
        connections.add(authToken, session);
        if (gameData == null) {
            Error error = new Error("Game does not exist");
            connections.sendToRoot(authToken, error);
            return;
        }
        if (authData == null) {
            Error error = new Error("Invalid AuthToken");
            connections.sendToRoot(authToken, error);
            return;
        }
        String playerName = authData.username();
        if (playerColor == ChessGame.TeamColor.WHITE && !Objects.equals(playerName, gameData.whiteUsername())) {
            Error error = new Error("That color is taken on this game");
            connections.sendToRoot(authToken, error);
            return;
        }
        if (playerColor == ChessGame.TeamColor.BLACK && !Objects.equals(playerName, gameData.blackUsername())) {
            Error error = new Error("That color is taken on this game");
            connections.sendToRoot(authToken, error);
            return;
        }
        LoadGame loadGame = new LoadGame(gameData);
        String message = String.format("%s joined the game as %s", playerName, playerColor);
        Notification serverMessage = new Notification(message);
        connections.sendToRoot(authToken, loadGame);
        connections.broadcast(authToken, serverMessage);
    }
    public void joinObserver(JoinObserver command, Session session) throws Exception {
        String authToken = command.getAuthString();
        AuthData authData = Service.authDAO.getAuthData(authToken);
        GameData gameData = Service.gameDAO.getGame(command.gameID);
        connections.add(authToken, session);
        if (gameData == null) {
            Error error = new Error("Game does not exist");
            connections.sendToRoot(authToken, error);
            return;
        }
        if (authData == null) {
            Error error = new Error("Invalid AuthToken");
            connections.sendToRoot(authToken, error);
            return;
        }
        String playerName = authData.username();
        String message = String.format("%s joined the game as an observer", playerName);
        ServerMessage serverMessage = new Notification(message);
        LoadGame loadGame = new LoadGame(gameData);
        connections.sendToRoot(authToken, loadGame);
        connections.broadcast(authToken, serverMessage);
    }
    public void makeMove(MakeMove command) throws Exception {
        GameData gameData = Service.gameDAO.getGame(command.gameID);
        AuthData authData = Service.authDAO.getAuthData(command.getAuthString());
        ChessMove move = command.move;
//        if (authData == null) {
//            Error error = new Error("Invalid AuthToken");
//            connections.sendToRoot(command.getAuthString(), error);
//            return;
//        }
        String playerName = authData.username();
//        if (gameData == null) {
//            Error error = new Error("Invalid Game");
//            connections.sendToRoot(command.getAuthString(), error);
//            return;
//        }
        ChessGame game = gameData.game();
//        if (game.isOver) {
//            Error error = new Error("This game is over");
//            connections.sendToRoot(authData.authToken(), error);
//            return;
//        }
//        if (!game.isValidMove(move)) {
//            Error error = new Error("Invalid move");
//            connections.sendToRoot(authData.authToken(), error);
//            return;
//        }
        String whiteUsername = gameData.whiteUsername();
        String blackUsername = gameData.blackUsername();
        if (!(Objects.equals(playerName, whiteUsername)) || !(Objects.equals(playerName, blackUsername))) {
            Error error = new Error("You are not playing");
            connections.sendToRoot(authData.authToken(), error);
            return;
        }
        game.makeMove(move);
        GameData newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game);
        Service.gameDAO.updateGame(newGame);
        String message = String.format("%s just made a move, %s", playerName, command.move.toString());
        ServerMessage serverMessage = new Notification(message);
        LoadGame loadGame = new LoadGame(gameData);
        connections.broadcast(authData.authToken(), serverMessage);
        connections.broadcast(authData.authToken(), loadGame);
        connections.sendToRoot(authData.authToken(), loadGame);
    }
    public void leave(Leave command) throws Exception {
        String authToken = command.getAuthString();
        String playerName = Service.authDAO.getAuthData(authToken).username();
        String message = String.format("%s left the game", playerName);
        ServerMessage serverMessage = new Notification(message);
        connections.broadcast(authToken, serverMessage);
        connections.remove(authToken);
    }
    public void resign(Resign command) throws Exception {
        String authToken = command.getAuthString();
        GameData gameData = Service.gameDAO.getGame(command.gameID);
        AuthData authData = Service.authDAO.getAuthData(authToken);
        if (authData == null) {
            Error error = new Error("Invalid AuthToken");
            connections.sendToRoot(authToken, error);
            return;
        }
        if (gameData == null) {
            Error error = new Error("Game does not exist");
            connections.sendToRoot(authToken, error);
            return;
        }
        ChessGame game = gameData.game();
        String playerName = authData.username();
        if (!Objects.equals(playerName, gameData.whiteUsername()) && gameData.whiteUsername() != null) {
            Error error = new Error("Can't resign unless you are playing");
            connections.sendToRoot(authToken, error);
            return;
        }
        if (!Objects.equals(playerName, gameData.blackUsername()) && gameData.blackUsername() != null) {
            Error error = new Error("Can't resign unless you are playing");
            connections.sendToRoot(authToken, error);
            return;
        }
        if (game.isOver) {
            Error error = new Error("This game is already over");
            connections.sendToRoot(authToken, error);
            return;
        }
        String message = String.format("%s resigned the game", playerName);
        game.endGame();
        GameData newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game);
        Service.gameDAO.updateGame(newGame);
        ServerMessage serverMessage = new Notification(message);
        connections.sendToRoot(authToken, serverMessage);
        connections.broadcast(authToken, serverMessage);
    }
}
