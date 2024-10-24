package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {
    private final AuthDAO authDao;
    private final GameDAO gameDao;
    private final UserDAO userDao;

    ClearServiceTest(AuthDAO authDao, GameDAO gameDao, UserDAO userDao) {
        this.authDao = authDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }


    @Test
    void clear() throws DataAccessException {
        UserData userData = new UserData("Bowen", "123", "123@gmail.com");
        userDao.createUser(userData);

        Assertions.assertNotNull(userDao.getUser(userData.username()));

        ClearService clearService = new ClearService(userDao,gameDao,authDao);
        clearService.clear();

        Assertions.assertNull(userDao.getUser(userData.username()));

    }
}