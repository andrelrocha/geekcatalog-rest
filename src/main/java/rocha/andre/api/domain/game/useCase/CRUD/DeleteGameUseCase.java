package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class DeleteGameUseCase {
    @Autowired
    private GameRepository repository;

    public void deleteGame(Long id) {
        var game = repository.findById(id)
                .orElseThrow(() -> new ValidationException("Il n'y a pas des jeux avec l'id informée."));

        repository.delete(game);
    }

}
