package rocha.andre.api.domain.game.DTO;

import rocha.andre.api.domain.game.Game;

import java.util.UUID;

public record GameAndIdDTO(UUID id, String name) {
    public GameAndIdDTO(Game game) {
        this(game.getId(), game.getName());
    }
}
