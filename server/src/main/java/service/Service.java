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

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public GameDAO getGameDAO() {
        return gameDAO;
    }

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public AuthDAO getAuthDAO() {
        return authDAO;
    }

    public void setAuthDAO(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }
}
