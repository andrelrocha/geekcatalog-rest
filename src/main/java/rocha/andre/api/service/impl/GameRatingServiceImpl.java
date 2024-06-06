package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameRating.DTO.GameRatingDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingReturnDTO;
import rocha.andre.api.domain.gameRating.useCase.AddGameRating;
import rocha.andre.api.service.GameRatingService;

@Service
public class GameRatingServiceImpl implements GameRatingService {
    @Autowired
    private AddGameRating addGameRating;

    @Override
    public GameRatingReturnDTO addGameRating(GameRatingDTO data) {
        return addGameRating.addGameRating(data);
    }
}
