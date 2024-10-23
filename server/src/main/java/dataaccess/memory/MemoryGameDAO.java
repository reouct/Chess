package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.HashSet;
import java.util.Set;

public class MemoryGameDAO implements GameDAO {

    private Set<GameData> game = new HashSet<>();

    @Override
    public void clear()  {
    game = new HashSet<>();
    }

    @Override
    public void createGame(GameData data) {

    }

    @Override
    public GameData getGame(GameData data) {
        return null;
    }

    @Override
    public Set<GameData> listGames() throws DataAccessException {
        HashSet<GameData> gameDataHashSet = new HashSet<>();
        for (GameData g : game) {
            GameData gameData = new GameData(g.gameID(),g.whiteUsername(),g.blackUsername(),g.gameName(), null);
            gameDataHashSet.add(gameData);
        }
        return gameDataHashSet;
    }

    @Override
    public void updateGame(GameData data) {

    }
}
