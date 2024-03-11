package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;


@Component
public class GetRandomGame {
    @Autowired
    private GameRepository repository;

    public GameReturnDTO suggestionGame() {
        var gameSuggestion = repository.findRandomGame();

        if (gameSuggestion == null) {
            gameSuggestion = repository.findRandomGame();
        }

        return new GameReturnDTO(gameSuggestion);
    }
}
