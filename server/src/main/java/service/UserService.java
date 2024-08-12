package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.Objects;
import java.util.UUID;

public class UserService extends Service {

    AuthService authService = new AuthService();

    public AuthData register(UserData userData) throws DataAccessException {
        if (userData.getUsername() == null || userData.getPassword() == null) {
            throw new DataAccessException("Error: Username/password required");
        }

        UserData user = userDAO.readUser(userData.getUsername());
        if (user != null) {
            throw new DataAccessException("Error: Username already exists");
        }

        // make sure we can create a user before registering all data
        if (userDAO.createUser(userData)) {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData();
            authData.setUsername(userData.getUsername());
            authData.setAuthToken(authToken);

            authDAO.createAuth(authData);
            return authData;
        }

        throw new DataAccessException("Error: New user not created");

    }

    public AuthData login(UserData userData) throws DataAccessException {
        if (userData.getUsername() == null || userData.getPassword() == null) {
            throw new DataAccessException("Error: Username/password required");
        }

        UserData user = userDAO.readUser(userData.getUsername());
        // check username and password before moving forward
        if (user == null) {
            throw new DataAccessException("Error: Username and/or password were incorrect");
        }

        if (!Objects.equals(user.getPassword(), userData.getPassword())) {
            throw new DataAccessException("Error: Username and/or password were incorrect");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData();
        authData.setUsername(userData.getUsername());
        authData.setAuthToken(authToken);

        // make sure that authentication happened
        if (authDAO.createAuth(authData)) {
            return authData;
        }

        throw new DataAccessException("Error: Authentication failed");
    }

    public boolean logout(AuthData authData) throws DataAccessException {
        String authToken = authService.userTokenAuthentication(authData.getAuthToken());
        authDAO.deleteAuth(authToken);
        return true;
    }

    public void clear() throws DataAccessException{
        userDAO.clear();
        authDAO.clear();
    }
}
