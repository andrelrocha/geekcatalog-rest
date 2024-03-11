package rocha.andre.api.domain.finished.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.finished.DTO.FinishedCreateDTO;
import rocha.andre.api.domain.finished.DTO.FinishedDTO;
import rocha.andre.api.domain.finished.DTO.FinishedReturnDTO;
import rocha.andre.api.domain.finished.Finished;
import rocha.andre.api.domain.finished.FinishedRepository;
import rocha.andre.api.domain.playingGame.PlayingGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class CreateFinished {
    @Autowired
    private FinishedRepository finishedRepository;

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayingGameRepository playingGameRepository;

    public FinishedReturnDTO createFinished (FinishedDTO data) {
        var opinionExists = finishedRepository.existsByGameId(data.gameId());

        if (opinionExists) {
            throw new ValidationException("Já existe uma opinião para o jogo informado.");
        }

        var game = gameRepository.findById(data.gameId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado."));

        String gameName = game.getName();

        if (!gameName.contains("(") || !gameName.contains(")")) {
            throw new ValidationException("O nome do jogo deve conter o console em parênteses.");
        }

        String[] parts = gameName.split("\\s*\\(\\s*");
        String name = parts[0].trim();
        String console = parts[1].replace(")", "").trim();

        var opinionCreateDTO = new FinishedCreateDTO(data, name, console, game);

        var opinion = new Finished(opinionCreateDTO);

        var opinionOnDB = finishedRepository.save(opinion);

        game.isDone();
        gameRepository.save(game);

        var playingGame = playingGameRepository.findByGameId(game.getId());
        playingGameRepository.deleteById(playingGame.getId());

        return new FinishedReturnDTO(opinionOnDB);
    }
}
