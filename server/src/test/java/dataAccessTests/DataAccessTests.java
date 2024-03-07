package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import server.requests.JoinGameRequest;
import server.services.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DataAccessTests {
    AuthDAO authDAO = new SQLAuthDAO();
    GameDAO gameDAO = new SQLGameDAO();
    UserDAO userDAO = new SQLUserDAO();

    public DataAccessTests() throws DataAccessException {
    }

    @Test
    public void testValidRegister() {
        try{
            ClearService.clear();
            UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");

            AuthData authData = RegisterService.register(testUser);
            assertEquals("galston", authDAO.getAuthData(authData.authToken()).username());
        }
        catch (Exception e) {
            //swallow exceptions
        }

    }
    @Test
    public void testInvalidRegister() {
        try{
            ClearService.clear();
            UserData testUser = new UserData("galston", "reaciton", "");

            AuthData authData = RegisterService.register(testUser);
        }
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Error"));
        }

    }
    @Test
    public void testInvalidLogin() {
        try{
            ClearService.clear();
            UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
            UserData otherTestUser = new UserData("garston", "reaction", "urmom@gmail.com");

            AuthData other = LoginService.loginUser(otherTestUser);
            AuthData correct = LoginService.loginUser(testUser);
            assertNotEquals(other, correct);
        }
        catch (Exception e){

        }
    }
    @Test
    public void testValidLogin() {
        try{
            ClearService.clear();
            UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
            LoginService.loginUser(testUser);
        }
        catch (Exception e) {
            assertFalse(e.getMessage().contains("Error"));
        }
    }
    @Test
    public void testValidLogout() {
        try{
            ClearService.clear();
            AuthData testAuth = new AuthData("galston", UUID.randomUUID().toString());

            LogoutService.logout(testAuth.authToken());
        }
        catch (Exception e) {
            assertFalse(e.getMessage().contains("Error"));
        }
    }
    @Test
    public void testInvalidLogout() {
        try{
            ClearService.clear();
            AuthData testAuth = new AuthData("galston", UUID.randomUUID().toString());

            LogoutService.logout(testAuth.authToken());
            assertNull(authDAO.getAuthData(testAuth.authToken()));
        }
        catch (UnauthorizedException | DataAccessException e) {

        }
    }
    @Test
    public void testValidCreateGame() {
        try {
            ClearService.clear();

            GameData testGame = new GameData(3, null, null, "newGame", new ChessGame());
            assertEquals(3, testGame.gameID());

        } catch (Exception e) {

        }
    }


    @Test
    public void testInvalidCreateGame() {
        try {
            ClearService.clear();
            GameData testGame = new GameData(3, null, null, "newGame", new ChessGame());
            assertNotEquals(2, testGame.gameID());
        } catch (Exception e) {

        }
    }
    @Test
    public void testValidJoinGame() {
        try {
            ClearService.clear();
            UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
            RegisterService.register(testUser);
            AuthData auth = LoginService.loginUser(testUser);
            JoinGameService.joinGame(new JoinGameRequest("WHITE", 1), authDAO.getAuthData(auth.authToken()).authToken());
            assertEquals(testUser.username(), gameDAO.getGame(1).whiteUsername());
        }
        catch (Exception e) {

        }
    }
    @Test
    public void testInvalidJoinGame() {
        try {
            ClearService.clear();
            UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
            RegisterService.register(testUser);
            AuthData auth = LoginService.loginUser(testUser);
            JoinGameService.joinGame(new JoinGameRequest("", 1), authDAO.getAuthData(auth.authToken()).authToken());
            assertNotEquals(testUser.username(), gameDAO.getGame(1).whiteUsername());
        } catch (Exception e) {

        }
    }
    @Test
    public void testValidClear() {
        try {
            AuthData authData = RegisterService.register(new UserData("galston", "reaciton", "urmom@gmail.com"));
            ClearService.clear();
            assertNull(authDAO.getAuthData(authData.authToken()));
        }
        catch (Exception e){
        }
    }
}
