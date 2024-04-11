package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    public int gameID;
    public JoinObserver(String authToken, Integer gameID) {
        super(authToken);
        setCommandType(CommandType.JOIN_OBSERVER);
        this.gameID = gameID;
    }
}
