package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;

public class SQLGameDAO implements GameDAO {


    @Override
    public void clear() throws DataAccessException {
        throw new RuntimeException("Method not implemented");
    }
}
