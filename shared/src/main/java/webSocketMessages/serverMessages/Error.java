package webSocketMessages.serverMessages;

import model.GameData;

public class Error extends ServerMessage{
    String errorMessage;
    public Error(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
}
