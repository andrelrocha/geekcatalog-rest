package rocha.andre.api.domain.finished.DTO;

import rocha.andre.api.domain.game.Game;

public record FinishedCreateDTO(String name,
                                String console,
                                int note,
                                String opinion,
                                Game game,
                                int metacritic,
                                String genre) {
    public FinishedCreateDTO(FinishedDTO dto, String name, String console, Game game) {
        this(name, console, dto.note(), dto.opinion(), game, game.getMetacritic(), game.getGenre());
    }
}
