package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.UserDAO;

public class SQLUserDAO implements UserDAO {
    @Override
    public void clear() throws DataAccessException {
        throw new RuntimeException("Method not implemented");
    }
}
