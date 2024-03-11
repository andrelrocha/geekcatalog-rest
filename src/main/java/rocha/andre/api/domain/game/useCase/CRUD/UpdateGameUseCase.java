package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;

@Component
public class UpdateGameUseCase {
    @Autowired
    private GameRepository repository;

    public GameReturnDTO updateGame(GameDTO dto, String gameId) {
        var gameIdLong = Long.parseLong(gameId);

        var game = repository.findById(gameIdLong)
                .orElseThrow(() -> new IllegalArgumentException("Game with the provided ID does not exist."));

        game.updateGame(dto);

        var gameOnDB = repository.save(game);

        return new GameReturnDTO(gameOnDB);
    }
}
