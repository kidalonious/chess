package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import server.requests.GameRequest;
import server.requests.JoinGameRequest;
import server.results.GameResult;
import server.requests.UserRequest;
import ui.ServerFacade;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        var serverUrl = "http://localhost:" + port;
        serverFacade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    public void clear() {
        try
        {
            serverFacade.clear();
        }
        catch (Exception ex)
        {
            fail();
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    public void loginPass()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";
        var authData = "";

        try {
            serverFacade.register(newRequest);
            authData = serverFacade.login(newRequest);
        }
        catch(Exception ex)
        {
            fail();
        }

        assertTrue(authData.length() > 10);
    }

    @Test
    public void loginFail()
    {

        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        UserRequest loginRequest = new UserRequest();
        loginRequest.username = "grant";
        loginRequest.password = "wrong";

        try {
            serverFacade.register(newRequest);
            serverFacade.login(loginRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }

    }

    @Test
    void registerPass() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        var authData = serverFacade.register(newRequest);
        assertTrue(authData.length() > 10);
    }

    @Test
    void registerFail() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        try {
            var authData = serverFacade.register(newRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }
    }

    @Test
    public void logoutPass()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";
        String message = "";

        try {
            serverFacade.register(newRequest);
            serverFacade.login(newRequest);
            serverFacade.logout(newRequest);
        }
        catch(Exception ex)
        {
            message = ex.getMessage();
        }

        assertEquals("", message);
    }

    @Test
    public void logoutFail()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        try {
            serverFacade.logout(newRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }

    }

    @Test
    public void createGamePass()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";
        int id = 0;

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        try {
            serverFacade.register(newRequest);
            serverFacade.login(newRequest);
            id = Integer.parseInt(serverFacade.createGame(gameRequest));
        }
        catch(Exception ex)
        {
            fail();
        }

        assertTrue(id > 0);
    }

    @Test
    public void createGameFail()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";
        int id = 0;

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        try {
            serverFacade.register(newRequest);
            id = Integer.parseInt(serverFacade.createGame(gameRequest));
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }
    }

    @Test
    public void listGamePass()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        try {
            serverFacade.register(newRequest);
            serverFacade.login(newRequest);
            serverFacade.createGame(gameRequest);
            //Collection<GameResult> gameList = serverFacade.listGames(gameRequest);
            //String gameListed = (String) gameList.toArray()[0];
            //assertNotEquals("newgame", gameList);
        }
        catch(Exception ex)
        {
            fail();
        }
    }

    @Test
    public void listGameFail()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        try {
            serverFacade.register(newRequest);
            serverFacade.login(newRequest);
            serverFacade.createGame(gameRequest);
            serverFacade.logout(newRequest);
            //Collection<GameResult> gameList = serverFacade.listGames(gameRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }

    }

    @Test
    public void joinGameFail()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";


        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 5);

        try {
            serverFacade.register(newRequest);
            serverFacade.login(newRequest);
            serverFacade.createGame(gameRequest);
            serverFacade.joinGame(joinGameRequest);
        }
        catch(Exception ex)
        {
            assertTrue(ex.getMessage().contains("failure"));
        }
    }

    @Test
    public void joinGamePass()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";
        String message = "";

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 3);

        try {
            serverFacade.register(newRequest);
            serverFacade.login(newRequest);
            serverFacade.createGame(gameRequest);
            serverFacade.joinGame(joinGameRequest);
        }
        catch(Exception ex)
        {
            message = ex.getMessage();
        }
        assertFalse(message.isEmpty());
    }


}