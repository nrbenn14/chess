package service;

import dataaccess.DataAccessException;
import model.AuthData;

public class AuthService extends Service {
    String tokenAuthentication(String authToken) throws DataAccessException {
        AuthData authData = authDAO.readAuth(authToken);
        if (authData != null) {
            return authData.getUsername();
        }
        throw new DataAccessException("Error: unauthorized");
    }
}
