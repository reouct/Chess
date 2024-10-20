package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.UserData;

public interface UserDAO {
    void clear() throws DataAccessException;

    public void createUser(UserData data);
    public UserData getUser(UserData data);
}
