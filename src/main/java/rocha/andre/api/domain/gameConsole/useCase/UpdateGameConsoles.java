package rocha.andre.api.domain.gameConsole.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameConsole.DTO.CreateGameConsoleDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.DTO.UpdateGameConsoleDTO;
import rocha.andre.api.domain.gameConsole.GameConsole;
import rocha.andre.api.domain.gameConsole.GameConsoleRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

@Component
public class UpdateGameConsoles {
    @Autowired
    private GameConsoleRepository gameConsoleRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ConsoleRepository consoleRepository;

    @Autowired
    private GetAllGameConsolesByGameID getAllGameConsolesByGameID;

    public Page<GameConsoleReturnDTO> updateGameConsoles(UpdateGameConsoleDTO data, String gameId) {
        var gameIdUUID = UUID.fromString(gameId);
        var pageable = PageRequest.of(0, 50);

        var consoles = gameConsoleRepository.findAllConsoleIdsByGameId(gameIdUUID);

        var consolesIdDataUUID = new ArrayList<UUID>();
        for (String consoleId : data.consoles()) {
            var consoleIdUUID = UUID.fromString(consoleId);
            consolesIdDataUUID.add(consoleIdUUID);
        }

        var game = gameRepository.findById(gameIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado id do jogo no update game console"));

        if (new HashSet<>(consoles).containsAll(consolesIdDataUUID) && new HashSet<>(consolesIdDataUUID).containsAll(consoles)) {
            return null;
        } else {
            if (!new HashSet<>(consoles).containsAll(consolesIdDataUUID)) {
                for (String consoleId : data.consoles()) {
                    var consoleIdUUID = UUID.fromString(consoleId);
                    if (consoles.contains(consoleIdUUID)) {
                        continue;
                    }
                    var console = consoleRepository.findById(consoleIdUUID)
                            .orElseThrow(() -> new ValidationException("Não foi encontrado id do console no update game console"));

                    var gameConsoleDTO = new CreateGameConsoleDTO(game, console);
                    var gameConsole = new GameConsole(gameConsoleDTO);
                    gameConsoleRepository.save(gameConsole);
                }
            }
            if (!new HashSet<>(consolesIdDataUUID).containsAll(consoles)) {
                gameConsoleRepository.deleteConsolesByGameIdAndConsoleIds(gameIdUUID,consolesIdDataUUID);
            }
        }

        return getAllGameConsolesByGameID.getAllGameConsolesByGameId(gameId, pageable);
    }
}
