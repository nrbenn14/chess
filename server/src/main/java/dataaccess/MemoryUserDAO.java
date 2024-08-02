package dataaccess;


import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
public class MemoryUserDAO implements UserDAO {
    public ArrayList<UserData> data;
    public MemoryUserDAO() {
        data = new ArrayList<>();
    }

    @Override
    public boolean createUser(UserData user) throws DataAccessException {
        if (readUser(user.getUsername()) == null) {
            return data.add(user);
        }
        throw new DataAccessException("User already exists.");
    }

    @Override
    public UserData readUser(String username) {
        for (UserData user : data) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        data = new ArrayList<>();
    }
}
