package rocha.andre.api.domain.consoles.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.GameConsoleRepository;

import java.util.UUID;

@Component
public class GetAllConsolesByGameId {
    @Autowired
    private ConsoleRepository consoleRepository;
    @Autowired
    private GameConsoleRepository gameConsoleRepository;

    public Page<ConsoleReturnDTO> getAllConsolesByGameId(Pageable pageable, String gameId) {
        var gameIdUUID = UUID.fromString(gameId);

        var consolesByGame = gameConsoleRepository.findAllGameConsolesByGameId(gameIdUUID, pageable).map(gameConsole -> {
            var console = new ConsoleReturnDTO(gameConsole.getConsole().getId(), gameConsole.getConsole().getName());
            return console;
        });

        return consolesByGame;
    }
}