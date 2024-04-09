package ui;

import ui.webSocket.ServerMessageHandler;
import ui.webSocket.WebSocketFacade;

public class BaseClient {
    private String username = null;
    ServerFacade server;
    States state = States.LOGGEDOUT;
    private String serverUrl;
    WebSocketFacade webSocket;
    ServerMessageHandler serverMessageHandler;
    String authToken;

    public BaseClient() {

    }

    public BaseClient(String serverURL) {
        server = new ServerFacade(serverURL);
    }
    static void print() {
        System.out.print("\n" + EscapeSequences.RESET_TEXT_COLOR + ">>> " + EscapeSequences.SET_TEXT_COLOR_RED);
    }
    public String configWebSocket(String serverURL) {
        try {
            this.webSocket = new WebSocketFacade(serverURL, serverMessageHandler);
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return "Client WebSocket Connection Established";
    }
}
