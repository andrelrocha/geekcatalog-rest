package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class CreateGameUseCase {
    @Autowired
    private GameRepository repository;

    public GameReturnDTO createGame(GameDTO data) {
        var gameExistsByName = repository.existsByName(data.name());

        if (gameExistsByName) {
            throw new ValidationException("Já existe um jogo com o nome " + data.name() + " cadastrado no banco.");
        }

        var game = new Game(data, false);

        var gameOnDB = repository.save(game);

        return new GameReturnDTO(gameOnDB);
    }
}
