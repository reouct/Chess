package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import model.AuthData;

import java.util.Objects;

public class LogoutService {
    private final AuthDAO authDao;

    public LogoutService(AuthDAO authDao) {
        this.authDao = authDao;
    }


    public void logout(String authToken) throws DataAccessException {
        AuthData authdata = authDao.getAuth(authToken);

        if (authToken == null || authdata == null || authdata.authToken() == null || !Objects.equals(authdata.authToken(), authToken)){
            throw new DataAccessException("Error: unauthorized");
        }

        authDao.deleteAuth(authdata);
    }
}
