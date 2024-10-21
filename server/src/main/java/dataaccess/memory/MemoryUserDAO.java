package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;
import model.UserData;

import java.util.HashSet;
import java.util.Set;

public class MemoryUserDAO implements UserDAO {
    private Set<UserData> user = new HashSet<>();

    @Override
    public void clear()  {
    user = new HashSet<>();
    }

    @Override
    public void createUser(UserData data) {
        user.add(data);
    }

    @Override
    public UserData getUser(UserData data) {
        return null;
    }
}
