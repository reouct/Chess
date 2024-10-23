package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.interfaces.GameDAO;
import model.GameData;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MemoryGameDAO implements GameDAO {

    private Set<GameData> game = new HashSet<>();

    @Override
    public void clear()  {
    game = new HashSet<>();
    }

    @Override
    public int createGame(GameData data) {
        for (GameData currentGame : game){
            if (Objects.equals(data.gameID(), currentGame.gameID())){
                return 0;
            }
        }
        game.add(data);
        return data.gameID();

    }

    @Override
    public GameData getGame(int gameID) {
        for (GameData g : game) {
            if (Objects.equals(g.gameID(), gameID)) {
                return g;
            }
        }
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
        for (GameData g : game) {
            if (g.gameID().equals(data.gameID())){
                game.remove(g);
                game.add(data);
            }
        }

    }
}
