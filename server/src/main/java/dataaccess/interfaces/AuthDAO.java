package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.AuthData;

public interface AuthDAO {
    public void clear() throws DataAccessException;

    public void createAuth(AuthData data);

    public void deleteAuth(AuthData data);

    public void getUser(AuthData data);
}
