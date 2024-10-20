package dataaccess.interfaces;

import dataaccess.DataAccessException;
import model.GameData;

import java.util.Set;

public interface GameDAO {
    public void clear() throws DataAccessException;

    public void createGame(GameData data);

    public GameData getGame(GameData data);

    public Set<GameData> listGames();

    public void updateGame(GameData data);
}
