package dataaccess.sql;

import dataaccess.interfaces.AuthDAO;
import model.AuthData;
import model.UserData;

public class SQLAuthDAO implements AuthDAO {

    @Override
    public void clear() {
        // need implementations
    }

    @Override
    public String createAuth(String username) {
        return "";
    }

    @Override
    public void deleteAuth(AuthData data) {
        // need implementations
    }

    @Override
    public AuthData getAuth(AuthData data) {
        return null;
    }

    @Override
    public AuthData getAuth(String data) {
        return null;
    }
}
