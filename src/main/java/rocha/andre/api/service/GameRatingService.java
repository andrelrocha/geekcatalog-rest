package rocha.andre.api.service;

import rocha.andre.api.domain.gameRating.DTO.*;

public interface GameRatingService {
    GameRatingReturnDTO addGameRating(GameRatingDTO data);
    AllRatingsGameDTO getAllRatingsByGameID(String gameId);
    GameRatingReturnDTO getRatingByGameAndUser(GameRatingByGameAndJWTDTO data);
    GameRatingAverageDTO getAverageRatingByJWT(String tokenJWT);
}
