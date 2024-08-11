package dataaccess;

import model.UserData;

public interface UserDAO {

    public boolean createUser(UserData userData) throws DataAccessException;
    public UserData readUser(String username) throws DataAccessException;
    public void clear() throws DataAccessException;
}
