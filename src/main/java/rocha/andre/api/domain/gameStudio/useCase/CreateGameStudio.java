package rocha.andre.api.domain.gameStudio.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameStudio.DTO.CreateGameStudioDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioReturnDTO;
import rocha.andre.api.domain.gameStudio.GameStudio;
import rocha.andre.api.domain.gameStudio.GameStudioRepository;
import rocha.andre.api.domain.studios.StudioRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class CreateGameStudio {
    @Autowired
    private GameStudioRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private StudioRepository studioRepository;

    public GameStudioReturnDTO createGameStudio(GameStudioDTO data) {
        var gameIdUUID = UUID.fromString(data.gameId());
        var studioIdUUID = UUID.fromString(data.studioId());

        var entityAlreadyCreated = repository.existsByGameIdAndStudioId(gameIdUUID, studioIdUUID);

        if (entityAlreadyCreated) {
            throw new ValidationException("Já existe registro com o game id e o studio id informado");
        }

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado ao tentar criar gamestudio"));

        var studio = studioRepository.findById(studioIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado studio com o id informado ao tentar criar gamestudio"));

        var createDTO = new CreateGameStudioDTO(game, studio);

        var gameStudio = new GameStudio(createDTO);

        var gameStudioOnDB = repository.save(gameStudio);

        return new GameStudioReturnDTO(gameStudioOnDB);
    }

}
