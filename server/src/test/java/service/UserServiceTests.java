package service;

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
    public static void setup() throws Exception {
        userService = new UserService();
        userService.clear();
        userService.userDAO.createUser(user1);
        userService.userDAO.createUser(user2);
        userService.authDAO.createAuth(auth);
    }

    @Test
    @DisplayName("Register Test")
    public static void registerTest() throws Exception {
        UserData user = new UserData("sark", "bossman", "dillinger@encom.com");
        AuthData authData =  userService.register(user);

        Assertions.assertEquals(user.getUsername(), authData.getAuthToken());
        Assertions.assertNotNull(authData.getAuthToken());
    }
}
