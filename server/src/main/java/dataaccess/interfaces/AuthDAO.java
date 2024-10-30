package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.AuthData;

public interface AuthDAO {
    public void clear() throws DataAccessException;

    public String createAuth(String username);

    public void deleteAuth(AuthData data) throws DataAccessException;

    AuthData getAuth(String authToken);
}
