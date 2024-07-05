package rocha.andre.api.domain.gameRating.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameRating.DTO.GameRatingAverageDTO;
import rocha.andre.api.domain.gameRating.GameRating;
import rocha.andre.api.domain.gameRating.GameRatingRepository;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetAverageRatingByUserJWT {
    @Autowired
    private GameRatingRepository gameRatingRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;

    public GameRatingAverageDTO getAverageRatingByJWT(String tokenJWT) {
        var user = getUserByTokenJWT.getUserByID(tokenJWT);

        if (user == null) {
            throw new ValidationException("Não foi encontrado usuário para o id informado");
        }

        var userIdUUID = UUID.fromString(user.id());

        var allRatings = gameRatingRepository.findAllByUserId(userIdUUID);

        int sum = 0;

        for (GameRating rating : allRatings) {
            sum += rating.getRating();
        }

        var gamesRatedQuantity = allRatings.size();

        var averageRating = sum / gamesRatedQuantity;

        return new GameRatingAverageDTO(user.id(), averageRating, gamesRatedQuantity);
    }

}
