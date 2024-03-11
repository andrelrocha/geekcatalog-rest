package rocha.andre.api.domain.playingGame.DTO;

import rocha.andre.api.domain.game.Game;

import java.time.LocalDateTime;

public record PlayingGameAddDTO(Game game, LocalDateTime firstPlayed) {
}
