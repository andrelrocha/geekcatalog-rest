package rocha.andre.api.domain.playingGame.DTO;

import rocha.andre.api.domain.playingGame.PlayingGame;

import java.time.LocalDateTime;

public record PlayingGameReturnDTO(Long id, String name, int length, int metacritic, int excitement, String genre, LocalDateTime firstPlayed, Long gameId) {
    public PlayingGameReturnDTO(PlayingGame playingGame) {
        this(
                playingGame.getId(),
                playingGame.getGame().getName(),
                playingGame.getGame().getLength(),
                playingGame.getGame().getMetacritic(),
                playingGame.getGame().getExcitement(),
                playingGame.getGame().getGenre(),
                playingGame.getFirstPlayed(),
                playingGame.getGame().getId()
        );
    }
}
