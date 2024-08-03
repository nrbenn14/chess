package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

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

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
