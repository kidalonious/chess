package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    Integer gameID;
    public JoinObserver(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
    }
}
