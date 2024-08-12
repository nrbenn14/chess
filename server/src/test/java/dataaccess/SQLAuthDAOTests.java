package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.*;
import spark.utils.Assert;

import java.sql.SQLException;

public class SQLAuthDAOTests {
    private static AuthData authData;
    private static SQLAuthDAO sqlAuthDAO;

    @BeforeAll
    public static void init() throws Exception {
        authData = new AuthData();
        authData.setUsername("tron");
        authData.setAuthToken("abcde");
        sqlAuthDAO = new SQLAuthDAO();
        DatabaseManager.createDatabase();
    }

    @BeforeEach
    public void setup() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("TRUNCATE TABLE auth");
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Test
    @DisplayName("Create Auth")
    public void createAuth() throws Exception {
        sqlAuthDAO.createAuth(authData);

        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM auth");
            var result = statement.executeQuery();

            result.next();

            Assertions.assertEquals("tron", result.getString("username"));
            Assertions.assertEquals("abcde", result.getString("authToken"));
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Test
    @DisplayName("Repeat Authentication")
    public void repeatAuth() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)");
            statement.setString(1, authData.getAuthToken());
            statement.setString(2, authData.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }

        Assertions.assertThrows(DataAccessException.class, () -> sqlAuthDAO.createAuth(authData));
    }

    @Test
    @DisplayName("Read Auth")
    public void readAuth() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)");
            statement.setString(1, authData.getAuthToken());
            statement.setString(2, authData.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }

        AuthData authData1 = sqlAuthDAO.readAuth("abcde");

        Assertions.assertEquals("abcde", authData1.getAuthToken());
        Assertions.assertEquals("tron", authData1.getUsername());
    }

    @Test
    @DisplayName("Missing Auth")
    public void missingAuth() throws Exception {
        Assertions.assertNull(sqlAuthDAO.readAuth(authData.getAuthToken()));
    }

    @Test
    @DisplayName("Delete Auth")
    public void deleteAuth() throws Exception {
        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO auth (authToken, username) VALUES (?, ?)");
            statement.setString(1, authData.getAuthToken());
            statement.setString(2, authData.getUsername());
            statement.executeUpdate();
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }

        sqlAuthDAO.deleteAuth(authData.getAuthToken());

        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM auth WHERE authToken = ?");
            statement.setString(1, authData.getAuthToken());
            var result = statement.executeQuery();
            Assertions.assertFalse(result.isBeforeFirst());
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }

    @Test
    @DisplayName("Missing Auth - Delete")
    public void deleteMissingAuth() throws Exception {
        Assertions.assertThrows(DataAccessException.class, () -> sqlAuthDAO.deleteAuth("abcde"));
    }

    @Test
    @DisplayName("Clear Auth")
    public void clearAuth() throws Exception {
        AuthData authData1 = new AuthData();
        authData1.setAuthToken("fghij");
        authData1.setUsername("flynn");

        sqlAuthDAO.createAuth(authData1);
        sqlAuthDAO.createAuth(authData);
        sqlAuthDAO.clear();

        try (var connection = DatabaseManager.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM auth");
            var result = statement.executeQuery();

            Assertions.assertFalse(result.isBeforeFirst());
        }
        catch (SQLException sqlException) {
            throw new DataAccessException(sqlException.getMessage());
        }
    }
}
