package rocha.andre.api.domain.gameGenre.DTO;

import rocha.andre.api.domain.gameGenre.GameGenre;

public record GameGenreReturnDTO(String gameName, String genreName) {
    public GameGenreReturnDTO(GameGenre gameGenre) {
        this(gameGenre.getGame().getName(), gameGenre.getGenre().getName());
    }
}
