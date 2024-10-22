package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

public interface AuthDAO {
    public void clear() throws DataAccessException;

    public String createAuth(String username);

    public void deleteAuth(AuthData data);

    public AuthData getAuth(AuthData data);

    AuthData getAuth(String data);
}
