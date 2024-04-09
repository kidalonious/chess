package server.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.Timer;

@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> join_player();
            case LEAVE -> leave();
            case RESIGN -> resign();
            case MAKE_MOVE -> make_move();
            case JOIN_OBSERVER -> join_observer();
            default -> error();
        }
    }

    public void join_player() {

    }
    public void join_observer() {

    }
    public void make_move() {

    }
    public void leave() {

    }
    public void resign() {

    }
    public void error() {

    }
}
