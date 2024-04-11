package rocha.andre.api.domain.game.DTO;

import rocha.andre.api.domain.game.Game;

public record GameDTO(String name, int metacritic, int yearOfRelease) {
    public GameDTO(Game game) {
        this(game.getName(), game.getMetacritic(), game.getYearOfRelease());
    }
}
