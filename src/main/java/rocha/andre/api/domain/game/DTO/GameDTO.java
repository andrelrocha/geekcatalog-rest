package rocha.andre.api.domain.game.DTO;

import rocha.andre.api.domain.game.Game;

public record GameDTO(String name, int length, int metacritic, int excitement, boolean played, String genre) {
    public GameDTO(Game game) {
        this(game.getName(), game.getLength(), game.getMetacritic(), game.getExcitement(), game.isPlayed(), game.getGenre());
    }
}
