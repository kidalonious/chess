package results;
public class GameResult{

    public Integer gameID;
    public String blackUsername;
    public String whiteUsername;
    public String authToken;
    public String gameName;
    public String playerColor;

    public String toString() {
        return "GameID: " + gameID + "Game Name: " + gameName + "White Player: " + whiteUsername + "Black Player: " + blackUsername;
    }




}
