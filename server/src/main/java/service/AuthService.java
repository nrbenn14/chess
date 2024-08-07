package service;

import dataaccess.DataAccessException;
import model.AuthData;

import javax.xml.crypto.Data;

public class AuthService extends Service {
    String authTokenAuthentication(String authToken) throws DataAccessException {
        AuthData authData = authDAO.readAuth(authToken);
        if (authData != null) {
            return authData.getUsername();
        }
        throw new DataAccessException("Error: unauthorized");
    }

    String userTokenAuthentication(String authToken) throws DataAccessException {
        AuthData authData = authDAO.readAuth(authToken);
        if (authData != null) {
            return authData.getAuthToken();
        }
        throw new DataAccessException("Error: unauthorized");
    }
}
