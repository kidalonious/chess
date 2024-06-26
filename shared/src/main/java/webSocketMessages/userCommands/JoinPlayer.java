package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand{
    public int gameID;
    public ChessGame.TeamColor playerColor;
    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        setCommandType(CommandType.JOIN_PLAYER);
        this.gameID = gameID;
        this.playerColor = playerColor;
    }
}
