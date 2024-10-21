package dataaccess.memory;

import dataaccess.interfaces.AuthDAO;
import model.AuthData;

import java.util.HashSet;
import java.util.Set;

public class MemoryAuthDao implements AuthDAO {

    private Set<AuthData> auth = new HashSet<>();

    @Override
    public void clear() { auth = new HashSet<>();
    }

    @Override
    public void createAuth(AuthData data) {
    // needs implement
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
