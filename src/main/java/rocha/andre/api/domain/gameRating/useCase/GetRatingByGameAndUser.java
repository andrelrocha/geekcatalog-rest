package rocha.andre.api.domain.gameRating.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndUserDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingReturnDTO;
import rocha.andre.api.domain.gameRating.GameRating;
import rocha.andre.api.domain.gameRating.GameRatingRepository;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetRatingByGameAndUser {
    @Autowired
    private GameRatingRepository gameRatingRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;

    public GameRatingReturnDTO getRatingByGameAndUser(GameRatingByGameAndUserDTO data) {
        var gameIdUUID = UUID.fromString(data.gameId());

        GameRating gameRating;

        if (data.tokenJWT().isPresent()) {
            var user = getUserByTokenJWT.getUserByID(String.valueOf(data.tokenJWT()));
            gameRating = gameRatingRepository.findByGameIdAndUserId(gameIdUUID, UUID.fromString(user.id()));
        } else if (data.userId().isPresent()) {
            var userIdUUID = data.userId();
            gameRating = gameRatingRepository.findByGameIdAndUserId(gameIdUUID, userIdUUID.get());
        } else {
            throw new RuntimeException("Não foi enviado nenhum dado sobre o usuário na requisição");
        }

        if (gameRating == null) {
            return new GameRatingReturnDTO(gameIdUUID, data.userId().get(), null, 0);
        }

        return new GameRatingReturnDTO(gameRating);
    }
}
