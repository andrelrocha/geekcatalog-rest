package rocha.andre.api.domain.game.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class UpdateGame {
    @Autowired
    private GameRepository repository;

    public GameReturnDTO updateGame(GameDTO data, String gameId) {
        var gameIdUUID = UUID.fromString(gameId);

        var game = repository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("No game was found for the provided ID."));

        game.updateGame(data);

        var gameOnDB = repository.save(game);

        return new GameReturnDTO(gameOnDB);
    }
}
