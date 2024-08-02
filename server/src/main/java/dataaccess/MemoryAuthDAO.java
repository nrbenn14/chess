package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO implements AuthDAO {
    public ArrayList<AuthData> authData;

    public MemoryAuthDAO() {
        authData = new ArrayList<>();
    }

    @Override
    public boolean createAuth(AuthData authToken) throws DataAccessException {
        if (readAuth(authToken.getAuthToken()) == null) {
            return authData.add(authToken);
        }

        throw new DataAccessException("User already authenticated");
    }

    @Override
    public AuthData readAuth(String authToken) {
        for (AuthData auth : authData) {
            if (auth.getAuthToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }


    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        AuthData authData1 = readAuth(authToken);
        if (authData1 != null) {
            authData.remove(authData1);
        }

        else {
            throw new DataAccessException("Auth data not found.");
        }
    }

    @Override
    public void clear() {
        authData = new ArrayList<>();
    }
}
