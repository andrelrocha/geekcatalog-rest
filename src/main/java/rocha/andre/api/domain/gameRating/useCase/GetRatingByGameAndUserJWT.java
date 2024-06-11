package rocha.andre.api.domain.gameRating.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndJWTDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndUserDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingReturnDTO;
import rocha.andre.api.domain.gameRating.GameRating;
import rocha.andre.api.domain.gameRating.GameRatingRepository;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;

import java.util.UUID;

@Component
public class GetRatingByGameAndUserJWT {
    @Autowired
    private GameRatingRepository gameRatingRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;

    public GameRatingReturnDTO getRatingByGameAndUser(GameRatingByGameAndJWTDTO data) {
        var gameIdUUID = UUID.fromString(data.gameId());

        var user = getUserByTokenJWT.getUserByID(data.tokenJWT());

        var gameRating = gameRatingRepository.findByGameIdAndUserId(gameIdUUID, UUID.fromString(user.id()));

        if (gameRating == null) {
            return new GameRatingReturnDTO(gameIdUUID, UUID.fromString(user.id()), user.name(), 0);
        }

        return new GameRatingReturnDTO(gameRating);
    }
}
