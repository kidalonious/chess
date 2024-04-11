import chess.*;
//import dataAccess.*;
//import server.*;
import ui.Repl;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
//        Server server = new Server();
//        server.run(8080);
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }
        try{


            new Repl(serverUrl).run();

        }
        catch(Exception ex) {
            System.exit(0);
        }

    }
}