package rocha.andre.api.domain.playingGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.playingGame.PlayingGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class DeletePlayingGame {
    @Autowired
    private PlayingGameRepository repository;

    public void deletePlayingGame(long id) {
        var gameExists = repository.existsById(id);

        if (!gameExists) {
            throw new ValidationException("Não foi encontrado jogo com o id informado");
        }

        repository.deleteById(id);
    }
}
