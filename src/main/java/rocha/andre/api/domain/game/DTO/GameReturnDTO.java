package rocha.andre.api.domain.game.DTO;

import rocha.andre.api.domain.game.Game;

import java.util.UUID;

public record GameReturnDTO(UUID id, String name, int metacritic, int yearOfRelease) {
    public GameReturnDTO(Game game) {
        this(game.getId(), game.getName(), game.getMetacritic(), game.getYearOfRelease());
    }
}
