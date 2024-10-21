package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

public interface AuthDAO {
    public void clear() throws DataAccessException;

    public String createAuth(UserData data);

    public void deleteAuth(AuthData data);

    public void getUser(AuthData data);
}
