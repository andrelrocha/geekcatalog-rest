package rocha.andre.api.domain.gameComment.DTO;

import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.user.User;

public record GameCommentDTO(User user, Game game, String comment) {
}
