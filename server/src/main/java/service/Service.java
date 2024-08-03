package service;

import dataaccess.*;
import model.GameData;

public class Service {
    protected UserDAO userDAO;
    protected GameDAO gameDAO;
    protected AuthDAO authDAO;

    public Service() {
        userDAO = MemoryUserDAO.getMemoryUserDAO();
        gameDAO = MemoryGameDAO.getMemoryGameDAO();
        authDAO = MemoryAuthDAO.getMemoryAuthDAO();
    }
}
