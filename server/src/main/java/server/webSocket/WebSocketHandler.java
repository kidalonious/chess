package server.webSocket;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Server;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {
    SQLAuthDAO sqlAuthDAO;

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> join_player(userGameCommand.getAuthString(), session);
            case LEAVE -> leave(userGameCommand.getAuthString());
            case RESIGN -> resign(userGameCommand.getAuthString());
            case MAKE_MOVE -> make_move(userGameCommand.getAuthString());
            case JOIN_OBSERVER -> join_observer(userGameCommand.getAuthString(), session);
            default -> error();
        }
    }

    public void join_player(String authToken, Session session) throws Exception {
        connections.add(authToken, session);
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String message = String.format("%s joined the game", playerName);
        serverMessage.setMessage(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void join_observer(String authToken, Session session) throws Exception {
        connections.add(authToken, session);
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String message = String.format("%s joined the game as an observer", playerName);
        serverMessage.setMessage(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void make_move(String authToken) throws Exception {
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String message = String.format("%s just made a move", playerName);
        serverMessage.setMessage(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void leave(String authToken) throws Exception {
        connections.remove(authToken);
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String message = String.format("%s left the game", playerName);
        serverMessage.setMessage(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void resign(String authToken) throws Exception {
        String playerName = sqlAuthDAO.getAuthData(authToken).username();
        ServerMessage serverMessage = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        String message = String.format("%s resigned the game", playerName);
        serverMessage.setMessage(message);
        connections.broadcast(authToken, serverMessage);
    }
    public void error() {

    }
}
