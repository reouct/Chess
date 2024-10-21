package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import model.AuthData;
import model.UserData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MemoryAuthDao implements AuthDAO {

    private Set<AuthData> auth = new HashSet<>();

    @Override
    public void clear() { auth = new HashSet<>();
    }

    @Override
    public String createAuth(UserData data) {
        String newToken = UUID.randomUUID().toString();
        auth.add(new AuthData(newToken, data.username()));
        return newToken;
    }

    @Override
    public void deleteAuth(AuthData data) {
    // needs implement
    }

    @Override
    public void getUser(AuthData data) {
    // need implement
    }
}
