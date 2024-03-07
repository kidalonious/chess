package dataAccess;

import model.GameData;
import server.requests.JoinGameRequest;

import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;

import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{

    public SQLGameDAO() throws DataAccessException {
        configureDatabase();
    }
    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE TABLE game";
        executeUpdate(statement);
    }

    @Override
    public void changeID() {

    }

    @Override
    public int createGame(GameData newGame) throws DataAccessException {
        var statement = "INSERT INTO game SET gameName=?";
        return executeUpdate(statement, newGame.gameName());
    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }

    @Override
    public Collection<GameData> listGames() {
        return null;
    }

    @Override
    public void joinGame(JoinGameRequest request, String authToken) throws DataAccessException {
        var statement = "";
        if (Objects.equals(request.playerColor(), "WHITE")) {
            statement = "UPDATE game SET whiteUsername=? WHERE gameID=?";
        }
        else if (Objects.equals(request.playerColor(), "BLACK")) {
            statement = "UPDATE game SET blackUsername=? WHERE gameID=?";
        }
        else return;
        String username = new SQLAuthDAO().getAuthData(authToken).username();
        executeUpdate(statement, username, request.gameID());
    }

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (Exception e) {
            throw new DataAccessException("Error: ");
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `game` (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) DEFAULT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`)
            )
            """
    };
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}
