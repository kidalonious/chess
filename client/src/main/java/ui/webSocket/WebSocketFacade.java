package ui.webSocket;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.ResponseException;
import server.webSocket.WebSocketHandler;
import ui.DrawnBoard;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

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
                            System.out.println("redrawing board");
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
    public void joinPlayer(String authToken) throws Exception{
        try {
            UserGameCommand userGameCommand = new UserGameCommand(authToken);
            userGameCommand.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void joinObserver(String authToken) throws Exception {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(authToken);
            userGameCommand.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void leave(String authToken) throws Exception {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(authToken);
            userGameCommand.setCommandType(UserGameCommand.CommandType.LEAVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void resign(String authToken) throws Exception {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(authToken);
            userGameCommand.setCommandType(UserGameCommand.CommandType.RESIGN);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
    public void makeMove(String authToken) throws Exception {
        try {
            UserGameCommand userGameCommand = new UserGameCommand(authToken);
            userGameCommand.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
            this.session.getBasicRemote().sendText(new Gson().toJson(userGameCommand));
        }
        catch (Exception e) {
            throw new ResponseException(e.getMessage());
        }
    }
}
