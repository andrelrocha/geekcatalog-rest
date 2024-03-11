package rocha.andre.api.domain.playingGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameReturnDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameUpdateDTO;
import rocha.andre.api.domain.playingGame.PlayingGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class UpdatePlayingGameUseCase {
    @Autowired
    private PlayingGameRepository repository;

    public PlayingGameReturnDTO updatePlayingGame(PlayingGameUpdateDTO data) {
        var game = repository.findById(data.id())
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo na lista 'jogando' com o id informado"));

        game.updatePlayingGame(data);

        var gameOnDB = repository.save(game);

        return new PlayingGameReturnDTO(gameOnDB);
    }
}
