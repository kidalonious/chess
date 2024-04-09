package webSocketMessages.serverMessages;

import model.GameData;

public class LoadGame extends ServerMessage{
    GameData game;
    public LoadGame(ServerMessageType type, GameData game) {
        super(type);
        this.game = game;
    }
}
