package server;

import server.handlers.*;
import server.webSocket.WebSocketHandler;
import spark.*;

public class Server {
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        Spark.webSocket("/connect", webSocketHandler);

        // Register your endpoints and handle exceptions here.
        //Clear
        Spark.delete("/db", ClearHandler::handle);
        //Register
        Spark.post("/user", RegisterHandler::handle);
        //Login
        Spark.post("/session", LoginHandler::handle);
        //Logout
        Spark.delete("/session", LogoutHandler::handle);
        //List Games
        Spark.get("/game", ListGamesHandler::handle);
        //Create Game
        Spark.post("/game", CreateGameHandler::handle);
        //Join Game
        Spark.put("/game", JoinGameHandler::handle);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}