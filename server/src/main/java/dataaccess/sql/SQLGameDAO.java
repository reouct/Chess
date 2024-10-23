package dataaccess.sql;

import dataaccess.interfaces.GameDAO;
import model.GameData;

import java.util.Set;

public class SQLGameDAO implements GameDAO {


    @Override
    public void clear() {
    }

    @Override
    public int createGame(GameData data) {

        return 0;
    }

    @Override
    public GameData getGame(int gameID) {
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
