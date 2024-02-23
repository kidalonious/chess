import chess.*;
import server.Server;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        Server new_server = new Server();
        new_server.run(5000);
        System.out.println("♕ 240 Chess Server: " + piece);
    }
}