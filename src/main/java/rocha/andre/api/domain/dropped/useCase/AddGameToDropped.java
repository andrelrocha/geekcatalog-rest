package rocha.andre.api.domain.dropped.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.dropped.DTO.DroppedCreateDTO;
import rocha.andre.api.domain.dropped.DTO.DroppedDTO;
import rocha.andre.api.domain.dropped.DTO.DroppedReturnDTO;
import rocha.andre.api.domain.dropped.Dropped;
import rocha.andre.api.domain.dropped.DroppedRepository;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.playingGame.PlayingGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class AddGameToDropped {
    @Autowired
    private DroppedRepository droppedRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayingGameRepository playingGameRepository;

    public DroppedReturnDTO addGameToDropped(DroppedDTO data) {
        var gameAlreadyDropped = droppedRepository.gameAlreadyDropped(data.gameId());

        if (gameAlreadyDropped) {
            throw new ValidationException("Esse jogo já foi abandonado");
        }

        var game = gameRepository.findById(data.gameId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado, no processo de add o jogo aos jogos abandonados."));

        var gameName = game.getName();

        if (!gameName.contains("(") || !gameName.contains(")")) {
            throw new ValidationException("O nome do jogo deve conter o console em parênteses.");
        }

        var parts = gameName.split("\\s*\\(\\s*");
        var name = parts[0].trim();
        var console = parts[1].replace(")", "").trim();

        var droppedDTOCreate = new DroppedCreateDTO(data, name, console, game);

        var droppedGame = new Dropped(droppedDTOCreate);

        var droppedGameOnDb = droppedRepository.save(droppedGame);
        game.isDone();

        var playingGame = playingGameRepository.findByGameId(game.getId());
        playingGameRepository.deleteById(playingGame.getId());

        return new DroppedReturnDTO(droppedGameOnDb);
    }

}