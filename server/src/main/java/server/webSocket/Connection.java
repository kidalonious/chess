package server.webSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;

public class Connection {
    public String authToken;
    public Session session;

    public Connection(String visitorName, Session session) {
        this.authToken = visitorName;
        this.session = session;
    }

    public void send(ServerMessage msg) throws IOException {
        String message = new Gson().toJson(msg);
        session.getRemote().sendString(message);
    }
}