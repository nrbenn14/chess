package service;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;
import service.UserService;

public class UserServiceTests {

    private static UserService userService;
    private static UserData user1;
    private static UserData user2;
    private static AuthData auth;

    @BeforeAll
    public static void init() {
        user1 = new UserData("tron", "fightforusers", "alan1@encom.com");
        user2 = new UserData("clu", "raindeerflotilla", "flynn@encom.com");
        auth = new AuthData();
        auth.setAuthToken("thegrid");
        auth.setUsername("tron");
    }

    @BeforeEach
    public void setup() throws Exception {
        userService = new UserService();
        userService.clear();
        userService.userDAO.createUser(user1);
        userService.userDAO.createUser(user2);
        userService.authDAO.createAuth(auth);
    }

    @Test
    @DisplayName("Register Test")
    public void registerTest() throws Exception {
        UserData user = new UserData("sark", "bossman", "dillinger@encom.com");


        try {
            AuthData authData =  userService.register(user);
            Assertions.assertEquals(user.getUsername(), authData.getUsername());
            Assertions.assertNotNull(authData.getAuthToken());
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Existing User Register")
    public void badRegister() throws Exception {
        UserData user = new UserData("tron", "uh-oh", "rinzler@grid.com");

        Assertions.assertThrows(DataAccessException.class, () -> userService.register(user));
    }

    @Test
    @DisplayName("Login Test")
    public void loginTest() throws Exception {
        try {
            AuthData authData = userService.login(user1);
            Assertions.assertEquals("tron", authData.getUsername());
            Assertions.assertNotNull(authData.getAuthToken());
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Bad Password")
    public void badLoginTest() throws Exception {
        UserData user = new UserData("tron", "nofightuserstoday", "alan1@encom.com");
        Assertions.assertThrows(DataAccessException.class, () -> userService.login(user));
    }

    @Test
    @DisplayName("Logout Test")
    public void logoutTest() throws Exception {
        try {
            Assertions.assertTrue(userService.logout(auth));
            Assertions.assertNull(userService.authDAO.readAuth(auth.getAuthToken()));
        }
        catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Bad Logout Test")
    public void badLogoutTest() throws Exception {
        userService.logout(auth);
        Assertions.assertThrows(DataAccessException.class, () -> userService.logout(auth));
    }

    @Test
    @DisplayName("Clear Test")
    public void clearTest() throws Exception {
        userService.clear();
        Assertions.assertNull(userService.authDAO.readAuth(auth.getAuthToken()));
        Assertions.assertNull(userService.userDAO.readUser("clu"));
        Assertions.assertNull(userService.userDAO.readUser("tron"));
    }
}
