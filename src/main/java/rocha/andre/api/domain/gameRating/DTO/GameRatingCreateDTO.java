package rocha.andre.api.domain.gameRating.DTO;

import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.user.User;

public record GameRatingCreateDTO(Game game, User user, int rating) {
}
