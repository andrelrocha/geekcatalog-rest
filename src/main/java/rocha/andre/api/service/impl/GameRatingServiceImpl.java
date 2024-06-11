package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameRating.DTO.*;
import rocha.andre.api.domain.gameRating.useCase.AddGameRating;
import rocha.andre.api.domain.gameRating.useCase.GetRatingByGameAndUserJWT;
import rocha.andre.api.domain.gameRating.useCase.GetRatingByGameId;
import rocha.andre.api.service.GameRatingService;

@Service
public class GameRatingServiceImpl implements GameRatingService {
    @Autowired
    private AddGameRating addGameRating;
    @Autowired
    private GetRatingByGameId getRatingByGameId;
    @Autowired
    private GetRatingByGameAndUserJWT getRatingByGameAndUserJWT;

    @Override
    public GameRatingReturnDTO addGameRating(GameRatingDTO data) {
        return addGameRating.addGameRating(data);
    }

    @Override
    public AllRatingsGameDTO getAllRatingsByGameID(String gameId) {
        return getRatingByGameId.getAllRatingsByGameID(gameId);
    }

    @Override
    public GameRatingReturnDTO getRatingByGameAndUser(GameRatingByGameAndJWTDTO data) {
        return getRatingByGameAndUserJWT.getRatingByGameAndUser(data);
    }
}
