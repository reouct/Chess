package result;

import model.GameData;

import java.util.Set;

public record ListGameResult(Set<GameData> games, String message) {
}
