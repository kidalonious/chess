package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import server.requests.GameRequest;
import server.requests.JoinGameRequest;
import server.results.GameResult;
import server.requests.UserRequest;
import ui.ServerFacade;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        var serverUrl = "http://localhost:" + port;
        facade = new ServerFacade(serverUrl);
    }

    @BeforeEach
    public void clear() {
        try
        {
            facade.clear();
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
            facade.register(newRequest);
            authData = facade.login(newRequest);
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
            facade.register(newRequest);
            facade.login(loginRequest);
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

        var authData = facade.register(newRequest);
        assertTrue(authData.length() > 10);
    }

    @Test
    void registerFail() throws Exception {
        UserRequest newRequest = new UserRequest();
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        try {
            var authData = facade.register(newRequest);
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
            facade.register(newRequest);
            facade.login(newRequest);
            facade.logout(newRequest);
        }
        catch(Exception ex)
        {
            message = ex.getMessage();
        }

        assertTrue(message.equals(""));
    }

    @Test
    public void logoutFail()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";

        try {
            facade.logout(newRequest);
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
            facade.register(newRequest);
            facade.login(newRequest);
            id = Integer.parseInt(facade.createGame(gameRequest));
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
            facade.register(newRequest);
            id = Integer.parseInt(facade.createGame(gameRequest));
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
        ArrayList gameList = new ArrayList();

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
        }
        catch(Exception ex)
        {
            fail();
        }

        assertEquals(0, gameList.size());
    }

    @Test
    public void listGameFail()
    {
        UserRequest newRequest = new UserRequest();
        newRequest.username = "grant";
        newRequest.password = "alstogra";
        newRequest.email = "grant@gmail.com";
        ArrayList gameList = new ArrayList();

        GameRequest gameRequest = new GameRequest();
        gameRequest.gameName = "newgame";

        try {
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            facade.logout(newRequest);
            gameList = facade.listGames(gameRequest);
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
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            facade.joinGame(joinGameRequest);
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
            facade.register(newRequest);
            facade.login(newRequest);
            facade.createGame(gameRequest);
            facade.joinGame(joinGameRequest);
        }
        catch(Exception ex)
        {
            message = ex.getMessage();
        }
        assertFalse(message.isEmpty());
    }


}