package dataaccess;

import model.AuthData;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {

    static {
        try {
            DatabaseManager.createDatabase();
        }
        catch (DataAccessException dataAccessException) {
            throw new RuntimeException(dataAccessException.getMessage());
        }
    }

    @Override
    public boolean createAuth(AuthData authData) throws DataAccessException {
        AuthData auth = readAuth(authData.getAuthToken());

        if (auth != null) {
            throw new DataAccessException("Error: user already authenticated");
        }

        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)");
            statement.setString(1, authData.getAuthToken());
            statement.setString(2, authData.getUsername());
            statement.executeUpdate();

            return true;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public AuthData readAuth(String authToken) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT authToken, username FROM auth WHERE authToken = ?");
            statement.setString(1, authToken);
            var result = statement.executeQuery();

            if (result.next()) {
                String sqlAuthToken = result.getString("authToken");
                String sqlUsername = result.getString("username");

                AuthData sqlAuthData = new AuthData();
                sqlAuthData.setAuthToken(sqlAuthToken);
                sqlAuthData.setUsername(sqlUsername);

                return sqlAuthData;
            }
            return null;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("DELETE FROM auth WHERE authToken = ?");
            statement.setString(1, authToken);

            int rows = statement.executeUpdate();
            if (rows <= 0) {
                throw new DataAccessException("Error: auth data not found.");
            }
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("TRUNCATE TABLE auth");
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }
}
