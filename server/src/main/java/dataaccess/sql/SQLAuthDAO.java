package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.AuthDAO;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void clear() throws DataAccessException {
        String statement = "Clear authtoken table";

    }
}
