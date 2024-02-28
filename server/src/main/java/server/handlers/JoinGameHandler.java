package server.handlers;

import com.google.gson.Gson;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DuplicateException;
import dataAccess.UnauthorizedException;
import server.requests.JoinGameRequest;
import server.services.JoinGameService;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class JoinGameHandler {
    public static Object handle(Request request, Response response) {
        try {
            Gson gson = new Gson();
            JoinGameRequest usableRequest = gson.fromJson((request.body()), JoinGameRequest.class);
            String authToken = request.headers("Authorization");

            JoinGameService.joinGame(usableRequest, authToken);
            response.status(200);
            return "";
        }
        catch (UnauthorizedException e) {
            response.status(401);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: unauthorized");
            return new Gson().toJson(errorMessage);
        }
        catch (BadRequestException e) {
            response.status(400);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: bad request");
            return new Gson().toJson(errorMessage);
        }
        catch (DuplicateException e) {
            response.status(403);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: already taken");
            return new Gson().toJson(errorMessage);
        }
        catch (Exception e) {
            response.status(500);
            Map<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error: " + e.getMessage());
            return new Gson().toJson(errorMessage);
        }


    }
}
