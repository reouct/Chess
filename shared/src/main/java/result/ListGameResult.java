package result;

import model.GameData;

import java.util.Set;

public record ListGameResult(Set<GameData> gameDataSet, String message) {
}
