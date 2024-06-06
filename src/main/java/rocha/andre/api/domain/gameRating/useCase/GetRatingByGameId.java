package rocha.andre.api.domain.gameRating.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameRating.DTO.AllRatingsGameDTO;
import rocha.andre.api.domain.gameRating.GameRating;
import rocha.andre.api.domain.gameRating.GameRatingRepository;

import java.util.UUID;

@Component
public class GetRatingByGameId {
    @Autowired
    private GameRatingRepository gameRatingRepository;

    public AllRatingsGameDTO getAllRatingsByGameID(String gameId) {
        var gameIdUUID = UUID.fromString(gameId);

        var allGameRatings = gameRatingRepository.findAllByGameId(gameIdUUID);

        var totalRatings = 0;
        var totalReviews = allGameRatings.size();

        for (GameRating rating : allGameRatings) {
            totalRatings += rating.getRating();
        }

        int averageRating = totalReviews > 0 ? totalRatings / totalReviews : 0;

        return new AllRatingsGameDTO(totalReviews, averageRating);
    }
}
