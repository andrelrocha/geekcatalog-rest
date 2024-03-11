package rocha.andre.api.domain.game.DTO;

import rocha.andre.api.domain.game.Game;

public record GameReturnDTO(Long id, String name, int length, int metacritic, int excitement, boolean played, String genre) {
    public GameReturnDTO(Game game) {
        this(game.getId(), game.getName(), game.getLength(), game.getMetacritic(), game.getExcitement(), game.isPlayed(), game.getGenre());
    }
}
