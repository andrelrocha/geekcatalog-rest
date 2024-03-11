package rocha.andre.api.domain.playingGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameAddDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameReturnDTO;
import rocha.andre.api.domain.playingGame.PlayingGame;
import rocha.andre.api.domain.playingGame.PlayingGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class AddPlayingGame {
    @Autowired
    private PlayingGameRepository playingGameRepository;
    @Autowired
    private GameRepository gameRepository;

    public PlayingGameReturnDTO addPlayingGame(PlayingGameDTO data) {
        var game = gameRepository.findById(data.gameId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o ID informado"));

        var playingGameDTO = new PlayingGameAddDTO(game, data.firstPlayed());

        var playingGame = new PlayingGame(playingGameDTO);

        var playingGameOnDB = playingGameRepository.save(playingGame);

        game.setPlayed();
        gameRepository.save(game);

        return new PlayingGameReturnDTO(playingGameOnDB);
    }
}
