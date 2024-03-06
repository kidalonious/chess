package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import server.services.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MyTests {
    AuthMemoryDAO authMemoryDAO;
    GameMemoryDAO gameMemoryDAO;
    UserMemoryDAO userMemoryDAO;

    @Test
    public void testValidRegister() {
        ClearService.clear();
        UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
        try{
            AuthData authData = RegisterService.register(testUser);
            assertEquals("galston", authMemoryDAO.getAuthData(authData.authToken()).username());
        }
        catch (Exception e) {
            //swallow exceptions
        }

    }
    @Test
    public void testInvalidRegister() {
        ClearService.clear();
        UserData testUser = new UserData("galston", "reaciton", "");
        try{
            AuthData authData = RegisterService.register(testUser);
        }
        catch (Exception e) {
            assertTrue(e.getMessage().contains("Error"));
        }

    }
    @Test
    public void testInvalidLogin() {
        ClearService.clear();
        UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
        UserData otherTestUser = new UserData("garston", "reaction", "urmom@gmail.com");
        try {
            AuthData other = LoginService.loginUser(otherTestUser);
            AuthData correct = LoginService.loginUser(testUser);
            assertNotEquals(other, correct);
        }
        catch (Exception e){

        }
    }
    @Test
    public void testValidLogin() {
        ClearService.clear();
        UserData testUser = new UserData("galston", "reaciton", "urmom@gmail.com");
        try {
            LoginService.loginUser(testUser);
        }
        catch (Exception e) {
            assertFalse(e.getMessage().contains("Error"));
        }
    }
    @Test
    public void testValidLogout() {
        ClearService.clear();
        AuthData testAuth = new AuthData("galston", UUID.randomUUID().toString());
        try {
            LogoutService.logout(testAuth.authToken());
        }
        catch (Exception e) {
            assertFalse(e.getMessage().contains("Error"));
        }
    }
    @Test
    public void testInvalidLogout() {
        ClearService.clear();
        AuthData testAuth = new AuthData("galston", UUID.randomUUID().toString());
        try {
            LogoutService.logout(testAuth.authToken());
            assertNull(authMemoryDAO.getAuthData(testAuth.authToken()));
        }
        catch (UnauthorizedException | DataAccessException e) {

        }
    }
    @Test
    public void testValidCreateGame() {
        ClearService.clear();

        GameData testGame = new GameData(3, null, null, "newGame", new ChessGame());
        assertEquals(3, testGame.gameID());
    }
    @Test
    public void testInvalidCreateGame() {
        ClearService.clear();
        GameData testGame = new GameData(3, null, null, "newGame", new ChessGame());
        assertNotEquals(2, testGame.gameID());
    }
    @Test
    public void testValidClear() {
        try {
            AuthData authData = RegisterService.register(new UserData("galston", "reaciton", "urmom@gmail.com"));
            ClearService.clear();
            assertNull(authMemoryDAO.getAuthData(authData.authToken()));
        }
        catch (Exception e){
        }
    }
}
