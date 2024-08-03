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
            throw new DataAccessException("Error: Username/password required");
        }

        UserData user = userDAO.readUser(userData.getUsername());
        if (user != null) {
            throw new DataAccessException("Error: Username already exists");
        }

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

        if (authDAO.createAuth(authData)) {
            return authData;
        }

        throw new DataAccessException("Error: Authentication failed");
    }

    public boolean logout(AuthData authData) throws DataAccessException {
        AuthData authData1 = authDAO.readAuth(authData.getAuthToken());
        String authToken = null;
        if (authData1 != null) {
            authToken = authData1.getAuthToken();
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
        authDAO.deleteAuth(authToken);
        return true;
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
