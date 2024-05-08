package rocha.andre.api.domain.gameGenre.DTO;

import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.genres.Genre;

public record CreateGameGenreDTO(Game game, Genre genre) {
}
