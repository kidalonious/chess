package dataAccess;

import model.AuthData;
import model.UserData;

import java.sql.Statement;
import java.util.UUID;

import static java.sql.Types.NULL;


public class SQLAuthDAO implements AuthDAO {

    public SQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }
    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    @Override
    public AuthData getAuthData(String authToken) throws DataAccessException {
//        var statement = "SELECT * FROM auth WHERE authToken=?";
//        var id = executeUpdate(statement, authToken);
//        if (id == 0) {
//            return null;
//        }
//        return new AuthData("username", authToken);
        return null;
    }

    @Override
    public AuthData addAuthData(UserData userData) throws DataAccessException {
        var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
        String authToken = generateAuthToken();
        executeUpdate(statement, userData.username(), authToken);
        return new AuthData(userData.username(), authToken);
    }

    @Override
    public String generateAuthToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void deleteAuth(String auth) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        executeUpdate(statement, auth);
    }

    private void executeUpdate(String statement, Object... params) throws DataAccessException {
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
                    rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `auth` (
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL,
              INDEX(authToken),
              INDEX(username)
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
