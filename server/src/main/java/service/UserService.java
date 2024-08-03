package service;

import dataaccess.UserDAO;

public class UserService extends Service {



    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}
