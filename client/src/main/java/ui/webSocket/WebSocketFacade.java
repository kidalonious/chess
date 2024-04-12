package ui.webSocket;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import exceptions.DataAccessException;
import exceptions.ResponseException;
import ui.DrawnBoard;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
public class WebSocketFacade extends Endpoint {

    Session session;
    ServerMessageHandler serverMessageHandler;


    public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) throws Exception {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.serverMessageHandler = serverMessageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()) {
                        case NOTIFICATION: {
                            Notification notification = new Gson().fromJson(message, Notification.class);
                            System.out.println(notification.message);
                            break;
                        }
                        case LOAD_GAME: {
                            LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
                            ChessGame game = loadGame.game.game();
                            System.out.println("redrawing board");
                            String[] startPosition = new String[]{"0"};

                            DrawnBoard.main(startPosition);
                            break;
                        }
                        case ERROR: {
                            Error error = new Gson().fromJson(message, Error.class);
                            System.out.println(error.errorMessage);
                            break;
                        }
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
    public void joinPlayer(String authToken, int gameID, ChessGame.TeamColor playerColor) throws Exception{
        try {
            JoinPlayer joinPlayer = new JoinPlayer(authToken, gameID, playerColor);
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void joinObserver(String authToken, int gameID) throws Exception {
        try {
            JoinObserver joinObserver = new JoinObserver(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(joinObserver));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void leave(String authToken) throws Exception {
        try {
            Leave leave = new Leave(authToken, 2);
            this.session.getBasicRemote().sendText(new Gson().toJson(leave));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void resign(String authToken, int gameID) throws Exception {
        try {
            Resign resign = new Resign(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(resign));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void makeMove(String authToken, int gameID, ChessMove move) throws Exception {
        try {
            MakeMove makeMove = new MakeMove(authToken, gameID, move);
            this.session.getBasicRemote().sendText(new Gson().toJson(makeMove));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
}
