package server.webSocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Server;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    SQLAuthDAO sqlAuthDAO;
    SQLGameDAO sqlGameDAO;

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER: {
                JoinPlayer command = new Gson().fromJson(message, JoinPlayer.class);
                join_player(command, session);
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
                make_move(command);
            }
            case JOIN_OBSERVER: {
                JoinObserver command = new Gson().fromJson(message, JoinObserver.class);
                join_observer(command, session);
            }
            default: {
                error();
            }
        }
    }

    public void join_player(JoinPlayer command, Session session) throws Exception {
        String authToken = command.getAuthString();
        ChessGame.TeamColor playerColor = command.playerColor;
        connections.add(authToken, session);
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        String message = String.format("%s joined the game as %s", playerName, playerColor);
        Notification serverMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, serverMessage);
    }
    public void join_observer(JoinObserver command, Session session) throws Exception {
        String authToken = command.getAuthString();
        connections.add(authToken, session);
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        String message = String.format("%s joined the game as an observer", playerName);
        ServerMessage serverMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, serverMessage);
    }
    public void make_move(MakeMove command) throws Exception {
        String authToken = command.getAuthString();
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        sqlGameDAO.updateGame(command.gameID, command.move);
        String message = String.format("%s just made a move", playerName);
        ServerMessage serverMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, serverMessage);
    }
    public void leave(Leave command) throws Exception {
        String authToken = command.getAuthString();
        connections.remove(authToken);
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        String message = String.format("%s left the game", playerName);
        ServerMessage serverMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, serverMessage);
    }
    public void resign(Resign command) throws Exception {
        String authToken = command.getAuthString();
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        String message = String.format("%s resigned the game", playerName);
        ServerMessage serverMessage = new Notification(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(authToken, serverMessage);
    }
    public void error() {
        ServerMessage serverMessage = new Error(ServerMessage.ServerMessageType.ERROR, "There was an error");

    }
}
