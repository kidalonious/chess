package webSocketMessages.serverMessages;

import model.GameData;

public class Error extends ServerMessage{
    String errorMessage;
    public Error(ServerMessageType type, String errorMessage) {
        super(type);
        this.errorMessage = errorMessage;
    }
}
