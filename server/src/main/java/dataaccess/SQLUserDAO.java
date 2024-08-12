package dataaccess;

import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    static {
        try {
            DatabaseManager.createDatabase();
        }
        catch (DataAccessException dataAccessException) {
            throw new RuntimeException(dataAccessException.getMessage());
        }
    }

    @Override
    public boolean createUser(UserData userData) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            if (readUser(userData.getUsername()) == null) {
                var statement = connection.prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?)");

                statement.setString(1, userData.getUsername());
                statement.setString(2, userData.getPassword());
                statement.setString(3, userData.getEmail());
                statement.executeUpdate();

                return true;
            }
            throw new DataAccessException("Error: user already exists");
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public UserData readUser(String username) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT username, password, email FROM user WHERE username = ?");

            statement.setString(1, username);

            var result = statement.executeQuery();

            if (result.next()) {
                return new UserData(
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email")
                );
            }

            return null;
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("DELETE FROM user");
            statement.executeUpdate();
        }
        catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
