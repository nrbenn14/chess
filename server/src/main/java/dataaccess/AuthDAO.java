package dataaccess;

import model.AuthData;

public interface AuthDAO {
    public boolean createAuth(AuthData authData) throws DataAccessException;
    public AuthData readAuth(String authToken);
    public void deleteAuth(String authToken) throws DataAccessException;
    public void clear();
}
