package rocha.andre.api.domain.gameRating.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndUserDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingReturnDTO;
import rocha.andre.api.domain.gameRating.GameRatingRepository;
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
        var user = getUserByTokenJWT.getUserByID(data.tokenJWT());

        var gameRating = gameRatingRepository.findByGameIdAndUserId(gameIdUUID, UUID.fromString(user.id()));

        if (gameRating == null) {
            throw new ValidationException("Não foi encontrado rating para o jogo informado, pelo usuário");
        }

        return new GameRatingReturnDTO(gameRating);
    }
}
