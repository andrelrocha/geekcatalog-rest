package rocha.andre.api.domain.gameGenre.DTO;

import rocha.andre.api.domain.gameGenre.GameGenre;

import java.util.UUID;

public record GameGenreReturnDTO(UUID id, String gameName, String genreName) {
    public GameGenreReturnDTO(GameGenre gameGenre) {
        this(gameGenre.getId(), gameGenre.getGame().getName(), gameGenre.getGenre().getName());
    }
}
