package dataaccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryAuthDAO extends AuthDAO {
    public Collection<AuthData> authData;

    public MemoryAuthDAO() {
        authData = new ArrayList<AuthData>();
    }

    @Override
    public void createAuth() {

    }

    @Override
    public void readAuth() {

    }

    @Override
    public void updateAuth() {

    }

    @Override
    public void deleteAuth() {

    }

    @Override
    public void clear() {
        authData.clear();
    }
}
