package dataaccess;


import model.UserData;

import java.util.ArrayList;
import java.util.Collection;
public class MemoryUserDAO implements UserDAO {
    public Collection<UserData> userData;
    public MemoryUserDAO() {
        userData = new ArrayList<UserData>();
    }

    @Override
    public void createUser() {
    }

    @Override
    public void readUser() {
    }

    @Override
    public void updateUser() {
    }

    @Override
    public void deleteUser() {
    }

    @Override
    public void clear() {
        userData.clear();
    }
}
