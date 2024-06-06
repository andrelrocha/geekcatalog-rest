package rocha.andre.api.domain.gameRating.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameRating.DTO.GameRatingCreateDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingReturnDTO;
import rocha.andre.api.domain.gameRating.GameRating;
import rocha.andre.api.domain.gameRating.GameRatingRepository;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class AddGameRating {
    @Autowired
    private GameRatingRepository gameRatingRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;

    public GameRatingReturnDTO addGameRating(GameRatingDTO data) {
        var gameIdUUID = UUID.fromString(data.gameId());
        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado, no processo de add rating"));

        var userIdUUID = UUID.fromString(data.userId());
        var user = userRepository.findById(userIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado usuário com o id informado, no processo de add rating"));

        var ratingExists = gameRatingRepository.existsByGameIdAndUserId(gameIdUUID, userIdUUID);

        GameRating gameRating;

        if (ratingExists) {
            gameRating = gameRatingRepository.findByGameIdAndUserId(game.getId(), user.getId());
            gameRating.updateGameRating(data.rating());
        } else {
            var gameRatingDTO = new GameRatingCreateDTO(game, user, data.rating());
            gameRating = new GameRating(gameRatingDTO);
        }

        var gameRatingOnDB = gameRatingRepository.save(gameRating);

        return new GameRatingReturnDTO(gameRatingOnDB);
    }

}
