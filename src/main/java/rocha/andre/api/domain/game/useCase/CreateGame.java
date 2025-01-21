package rocha.andre.api.domain.game.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class CreateGame {
    @Autowired
    private GameRepository repository;

    public GameReturnDTO createGame(GameDTO data) {
        var existsByName = repository.existsByName(data.name());

        if (existsByName) {
            throw new ValidationException("There's already a game with the provided name.");
        }

        var game = new Game(data);

        var gameOnDB = repository.save(game);

        return new GameReturnDTO(gameOnDB);
    }
}
