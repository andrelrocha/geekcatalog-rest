package rocha.andre.api.domain.gameRating.DTO;

import java.util.Optional;
import java.util.UUID;

public record GameRatingByGameAndUserDTO(String gameId, Optional<String> tokenJWT, Optional<UUID> userId) {
    public GameRatingByGameAndUserDTO(String gameId, String tokenJWT) {
        this(gameId, Optional.of(tokenJWT), Optional.empty());
    }

    public GameRatingByGameAndUserDTO(UUID gameId, UUID userId) {
        this(gameId.toString(), Optional.empty(), Optional.of(userId));
    }
}
