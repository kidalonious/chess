package server;

import server.handlers.ClearHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        //Clear
        Spark.delete("/db", ClearHandler::handle);
        //Register
        //Spark.post("/user", this::);
        //Login
        //Spark.post("/session", this::);
        //Logout
        //Spark.delete("/session", this::);
        //List Games
        //Spark.get("/game", this::);
        //Create Game
        //Spark.post("/game", this::);
        //Join Game
        //Spark.put("/game", this::);
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}