package rocha.andre.api.domain.playingGame.DTO;

import java.time.LocalDateTime;

public record PlayingGameUpdateDTO(long id, LocalDateTime firstPlayed) {
}
