package rocha.andre.api.domain.gameRating.DTO;

import rocha.andre.api.domain.gameRating.GameRating;

import java.util.UUID;

public record GameRatingReturnDTO(UUID gameId, UUID userId, String userName, int rating) {
    public GameRatingReturnDTO(GameRating gameRating) {
        this(gameRating.getGame().getId(), gameRating.getUser().getId(), gameRating.getUser().getName(), gameRating.getRating());
    }
}
