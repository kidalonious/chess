package ui;

import dataAccess.ResponseException;
import ui.webSocket.ServerMessageHandler;
import ui.webSocket.WebSocketFacade;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


public class InGameClient extends BaseClient{
    WebSocketFacade webSocket;
    ServerMessageHandler serverMessageHandler = new ServerMessageHandler();
    public InGameClient(String serverURL) {
        super(serverURL);
        configWebSocket(serverURL);
    }
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "redraw" -> redrawBoard(params);
                case "leave" -> leave(params);
                case "move" -> make_move(params);
                case "resign" -> resign(params);
                case "highlight" -> highlight(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
    public String help()
    {
        return """
                redraw < BLACK | WHITE | OBSERVER > - the chess board
                leave - your current game
                move - move a piece in the chess game
                resign - forfeit your current game
                highlight - possible moves for a piece
                help - show possible commands
                """;
    }
    public String redrawBoard(String ... params) throws Exception {
        if (params.length == 2) {
            if (Objects.equals(params[1], "black")) {
                //return DrawBoardReversed
            }
            else {
                //return DrawBoard
            }
        }

        throw new ResponseException("Please input a color you want to view the board from");
    }
    public String leave(String ... params) {
            Repl.state = States.LOGGEDIN;
            return "You left the game";
    }
    public String make_move(String ... params) throws Exception{

        throw new ResponseException("Expected different input");
    }
    public String resign(String ... params) throws Exception {
        if (params.length == 1) {
            var input = new Scanner(System.in);
            System.out.println("Are you sure? [y/n]");
            String name = input.nextLine();
            if (Objects.equals(name, "y")) {
                return "You lose!";
            }
            else {
                return "Game continues.";
            }
        }
        throw new ResponseException("Expected different input");
    }
    public String highlight(String ... params) throws Exception{
        throw new ResponseException("Expected different input");
    }
}