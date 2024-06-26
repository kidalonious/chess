package ui;

import chess.ChessGame;
import exceptions.ResponseException;
import requests.*;

import java.util.Arrays;
import java.util.Locale;

public class LoggedInClient extends BaseClient {

    public LoggedInClient(String serverURL) {
        super(serverURL);
        configWebSocket(serverURL);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> createGame(params);
                case "list" -> listGames(params);
                case "join" -> joinGame(params);
                case "observe" -> observeGame(params);
                case "logout" -> logout(params);
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String createGame(String... params) throws ResponseException {
        if(params.length == 1) {
            GameRequest newRequest = new GameRequest();
            newRequest.gameName = params[0];

            String gameID = server.createGame(newRequest);
            return String.format("The gameID of the game %s is %s", newRequest.gameName, gameID);
        }
        throw new ResponseException("Expected more game information");
    }

    public String listGames(String ... params) throws ResponseException{
        GameRequest newRequest = new GameRequest();

        var games = server.listGames(newRequest);
        var result = new StringBuilder();
        int i = 1;
        for (var game : games) {
            result.append("#" + i).append("\n   ").append("GameID: ").append(game.gameID()).append("\n   ").append("Game Name: ").append(game.gameName()).append("\n   ");
            result.append("White Player: ").append(game.whiteUsername()).append("\n   ").append("Black Player: ").append(game.blackUsername()).append("\n");
            i++;
        }
        return result.toString();

    }

    public String joinGame(String ... params) throws Exception{
        if (params.length >= 1) {
            GameRequest newRequest = new GameRequest();
            newRequest.gameID = Integer.parseInt(params[0]);

            if (params.length == 2) {
                newRequest.playerColor = params[1].toUpperCase(Locale.ROOT);
            }
            JoinGameRequest joinGameRequest = new JoinGameRequest(newRequest.playerColor, newRequest.gameID);
            server.joinGame(joinGameRequest);
            String[] startPosition = new String[]{"0"};

            DrawnBoard.main(startPosition);
            Repl.state = States.INGAME;
            Repl.inGameClient.setGameData(newRequest.gameID);
            ChessGame.TeamColor playerColor = ChessGame.TeamColor.valueOf(newRequest.playerColor);
            webSocket.joinPlayer(authToken, newRequest.gameID, playerColor);
            return String.format("\nYou joined the game as %s.", newRequest.playerColor);
        }
        throw new ResponseException("Expected more registration information.");
    }

    public String observeGame(String... params) throws Exception{
        if(params.length == 1)
        {
            joinGame(params);
            int gameID = Integer.parseInt(params[0]);
            Repl.state = States.INGAME;
            String[] startPosition = new String[]{"0"};
            DrawnBoard.main(startPosition);
            webSocket.joinObserver(authToken, gameID);
            return ("You joined the game as an observer");
        }
        throw new ResponseException("You cannot include a color as an observer.");
    }

    public String logout(String ... params) throws ResponseException{
        UserRequest newRequest = new UserRequest();
        server.logout(newRequest);

        Repl.state = States.LOGGEDOUT;
        return ("You are logged out.");
    }

    public String help()
    {
        return """
                create <NAME> - a game
                list - games
                join <ID> [BLACK | WHITE | <empty>] - a game
                observe <ID> - a game
                logout - when you are done
                quit - playing chess
                help - with possible commands
                """;
    }

}
