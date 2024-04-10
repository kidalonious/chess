package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.ResponseException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.services.Service;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
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
            }
            case LEAVE: {
                Leave command = new Gson().fromJson(message, Leave.class);
                leave(command);
            }
            case RESIGN: {
                Resign command = new Gson().fromJson(message, Resign.class);
                resign(command);
            }
            case MAKE_MOVE: {
                MakeMove command = new Gson().fromJson(message, MakeMove.class);
                makeMove(command);
            }
            case JOIN_OBSERVER: {
                JoinObserver command = new Gson().fromJson(message, JoinObserver.class);
                joinObserver(command, session);
            }
        }
    }

    public void joinPlayer(JoinPlayer command, Session session) throws Exception {
        String authToken = command.getAuthString();
        GameData game = Service.gameDAO.getGame(command.gameID);
        LoadGame loadGame = new LoadGame(game);
        ChessGame.TeamColor playerColor = command.playerColor;
        String playerName = Service.authDAO.getAuthData(authToken).username();
        String message = String.format("%s joined the game as %s", playerName, playerColor);
        Notification serverMessage = new Notification(message);
        connections.add(authToken, session);
        connections.sendToRoot(authToken, loadGame);
        connections.broadcast(authToken, serverMessage);
    }
    public void joinObserver(JoinObserver command, Session session) throws Exception {
        String authToken = command.getAuthString();
        String playerName = Service.authDAO.getAuthData(authToken).username();
        String message = String.format("%s joined the game as an observer", playerName);
        ServerMessage serverMessage = new Notification(message);
        connections.add(authToken, session);
        connections.broadcast(authToken, serverMessage);
    }
    public void makeMove(MakeMove command) throws Exception {
        String authToken = command.getAuthString();
        String playerName = Service.authDAO.getAuthData(authToken).username();
        Service.gameDAO.updateGame(command.gameID, command.move);
        String message = String.format("%s just made a move, %s", playerName, command.move.toString());
        ServerMessage serverMessage = new Notification(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void leave(Leave command) throws Exception {
        String authToken = command.getAuthString();
        connections.remove(authToken);
        String playerName = Service.authDAO.getAuthData(authToken).username();
        String message = String.format("%s left the game", playerName);
        ServerMessage serverMessage = new Notification(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void resign(Resign command) throws Exception {
        String authToken = command.getAuthString();
        String playerName = Service.authDAO.getAuthData(authToken).username();
        String message = String.format("%s resigned the game", playerName);
        ServerMessage serverMessage = new Notification(message);
        connections.broadcast(authToken, serverMessage);
    }

    /*@OnWebSocketError
    public void onError(Session session, Throwable e) throws Exception {
        session.getRemote().sendString("Websocket Error Occurred");
    } */

}
