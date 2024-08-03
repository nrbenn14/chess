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

    public AuthData register(UserData userData) throws DataAccessException {
        if (userData.getUsername() == null || userData.getPassword() == null) {
            throw new DataAccessException("Username/passwork required");
        }

        UserData user = userDAO.readUser(userData.getUsername());
        if (user != null) {
            throw new DataAccessException("Username already exists");
        }

        if (userDAO.createUser(userData)) {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData();
            authData.setUsername(userData.getUsername());
            authData.setAuthToken(authToken);

            authDAO.createAuth(authData);
            return authData;
        }

        throw new DataAccessException("New user not created");

    }

    public AuthData login(UserData userData) throws DataAccessException {
        if (userData.getUsername() == null || userData.getPassword() == null) {
            throw new DataAccessException("Username and password are required");
        }

        UserData user = userDAO.readUser(userData.getUsername());
        if (user == null) {
            throw new DataAccessException("Username and/or password were incorrect");
        }

        if (!Objects.equals(userData.getPassword(), user.getPassword())) {
            throw new DataAccessException("Password was incorrect");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData();
        authData.setUsername(userData.getUsername());
        authData.setAuthToken(authToken);

        if (authDAO.createAuth(authData)) {
            return authData;
        }

        throw new DataAccessException("Authentication failed");
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
