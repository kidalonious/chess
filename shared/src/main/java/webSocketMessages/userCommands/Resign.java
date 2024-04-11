package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
    public int gameID;
    public Resign(String authToken, Integer gameID) {
        super(authToken);
        setCommandType(CommandType.RESIGN);
        this.gameID = gameID;
    }
}
