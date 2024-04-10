package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    public Integer gameID;
    public ChessMove move;
    public MakeMove(String authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }
}
