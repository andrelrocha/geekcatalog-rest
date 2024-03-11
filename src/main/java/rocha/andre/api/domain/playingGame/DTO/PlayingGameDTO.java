package rocha.andre.api.domain.playingGame.DTO;

import java.time.LocalDateTime;

public record PlayingGameDTO(Long gameId, LocalDateTime firstPlayed) {
}
