package rocha.andre.api.domain.dropped.DTO;

import rocha.andre.api.domain.game.Game;

public record DroppedCreateDTO(String name, String console, int note, String reason, Game game) {
    public DroppedCreateDTO(DroppedDTO dto, String name, String console, Game game) {
        this(name, console, dto.note(), dto.reason(), game);
    }
}
