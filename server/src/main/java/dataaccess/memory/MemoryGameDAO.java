package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import model.GameData;

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
    public Set<GameData> listGames() {
        return Set.of();
    }

    @Override
    public void updateGame(GameData data) {

    }
}
