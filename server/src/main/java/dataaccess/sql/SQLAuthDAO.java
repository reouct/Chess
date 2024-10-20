package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;
import model.AuthData;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() throws DataAccessException {
        String statement = "Clear authtoken table";

    }

    @Override
    public void createAuth(AuthData data) {
        // need implementations
    }

    @Override
    public void deleteAuth(AuthData data) {
        // need implementations
    }

    @Override
    public void getUser(AuthData data) {
        // need implementations
    }
}
