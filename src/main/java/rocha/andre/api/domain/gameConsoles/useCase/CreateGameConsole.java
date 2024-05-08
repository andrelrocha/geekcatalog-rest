package rocha.andre.api.domain.gameConsoles.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameConsoles.DTO.CreateGameConsoleDTO;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsoles.GameConsole;
import rocha.andre.api.domain.gameConsoles.GameConsoleRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class CreateGameConsole {
    @Autowired
    private GameConsoleRepository repository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ConsoleRepository consoleRepository;

    public GameConsoleReturnDTO createGameConsole(GameConsoleDTO data) {
        var gameIdUUID = UUID.fromString(data.gameId());
        var consoleIdUUID = UUID.fromString(data.consoleId());

        var entityAlreadyCreated = repository.existsByGameIdAndConsoleId(gameIdUUID, consoleIdUUID);

        if (entityAlreadyCreated) {
            throw new ValidationException("Já existe registro com o game id e o console id informado");
        }

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado ao tentar criar gameconsole"));

        var console = consoleRepository.findById(consoleIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado console com o id informado ao tentar criar gameconsole"));

        var createDTO = new CreateGameConsoleDTO(game, console);

        var gameConsole = new GameConsole(createDTO);

        var gameConsoleOnDB = repository.save(gameConsole);

        return new GameConsoleReturnDTO(gameConsoleOnDB);
    }

}
