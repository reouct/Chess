package service;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import model.AuthData;
import model.UserData;
import model.UsernameAndPassword;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;

public class LoginService {
    private final UserDAO userDao;
    private final AuthDAO authDao;


    public LoginService(UserDAO userDao, AuthDAO authDao) {
        this.userDao = userDao;
        this.authDao = authDao;
    }

    public AuthData loginUser(UsernameAndPassword usernameAndPassword) throws DataAccessException{
        UserData userdata = userDao.getUser(usernameAndPassword.username());

        if (userdata == null || !BCrypt.checkpw(usernameAndPassword.password(), userdata.password())){
            throw new DataAccessException("Error: unauthorized");
        }
        String authtoken = authDao.createAuth(usernameAndPassword.username());
        return new AuthData(authtoken,usernameAndPassword.username());
    }

}
