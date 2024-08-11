package dataaccess;

import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    @Override
    public boolean createUser(UserData userData) throws DataAccessException {
        DatabaseManager.createDatabase();
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
    public UserData readUser(String username) {
        return null;
    }

    @Override
    public void clear() {

    }
}
