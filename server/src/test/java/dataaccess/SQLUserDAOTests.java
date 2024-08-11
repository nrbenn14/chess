package dataaccess;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLUserDAOTests {

    private static AuthData user;
    private static UserData userData;
    private static SQLUserDAO sqlUserDAO;
    private static SQLAuthDAO sqlAuthDAO;
    private static SQLGameDAO sqlGameDAO;

    @BeforeAll
    public static void init() throws Exception {
        user = new AuthData();
        user.setUsername("flynn");
        user.setAuthToken("abcde");

        userData = new UserData("flynn", "raindeerflotilla", "flynn@encom.com");
        sqlUserDAO = new SQLUserDAO();
        sqlAuthDAO = new SQLAuthDAO();
        sqlGameDAO = new SQLGameDAO();
        DatabaseManager.createDatabase();
    }

    @BeforeEach
    public void setup() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            truncateTable(connection, "user");
            truncateTable(connection, "auth");
            truncateTable(connection, "game");
        }
        catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    private static void truncateTable(Connection connection, String string) throws SQLException {
        var statement = connection.prepareStatement("TRUNCATE TABLE " + string);
        statement.executeUpdate();
    }

    @Test
    @DisplayName("Create User")
    public void createUser() throws Exception {
        sqlUserDAO.createUser(userData);

        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM  user WHERE username = ?");
            statement.setString(1, "flynn");
            var result = statement.executeQuery();
            result.next();

            String username = result.getString("username");
            String password = result.getString("password");
            String email = result.getString("email");

            Assertions.assertEquals("flynn", username);
            Assertions.assertEquals("raindeerflotilla", password);
            Assertions.assertEquals("flynn@encom.com", email);
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }
}
