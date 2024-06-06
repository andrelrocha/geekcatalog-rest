package rocha.andre.api.service;

import rocha.andre.api.domain.gameRating.DTO.AllRatingsGameDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingReturnDTO;

public interface GameRatingService {
    GameRatingReturnDTO addGameRating(GameRatingDTO data);
    AllRatingsGameDTO getAllRatingsByGameID(String gameId);
}
