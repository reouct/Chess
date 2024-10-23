package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.GameData;

import java.util.Set;

public interface GameDAO {
    public void clear() throws DataAccessException;

    public int createGame(GameData data);

    public GameData getGame(int gameID);

    public Set<GameData> listGames() throws DataAccessException;

    public void updateGame(GameData data);
}
