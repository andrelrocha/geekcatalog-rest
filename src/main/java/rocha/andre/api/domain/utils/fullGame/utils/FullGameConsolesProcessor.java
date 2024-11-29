package rocha.andre.api.domain.utils.fullGame.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.utils.API.IGDB.utils.ConsoleNameFormatterFromIGDB;
import rocha.andre.api.service.ConsoleService;
import rocha.andre.api.service.GameConsoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class FullGameConsolesProcessor {
    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private ConsoleNameFormatterFromIGDB consoleConverter;
    @Autowired
    private GameConsoleService gameConsoleService;

    private static final Logger logger = LoggerFactory.getLogger(FullGameConsolesProcessor.class);

    public ArrayList<ConsoleReturnDTO> processFullGameConsoles(List<String> consoles, String gameId) {
        var normalizedConsolesWithSystemNomenclature = consoleConverter.normalizeAndConvertNames(consoles);

        var consolesMapWithId = fetchConsolesWithId(normalizedConsolesWithSystemNomenclature);

        var newGameConsoles = new ArrayList<ConsoleReturnDTO>();

        for (String consoleName : normalizedConsolesWithSystemNomenclature) {
            ConsoleReturnDTO console = consolesMapWithId.getOrDefault(consoleName, null);

            console = handleConsoleCreationOrFetch(consoleName, console, gameId);

            addGameConsole(newGameConsoles, gameId, console);
        }

        return newGameConsoles;
    }

    private Map<String, ConsoleReturnDTO> fetchConsolesWithId(List<String> normalizedConsoles) {
        ArrayList<ConsoleDTO> consoleDTOs = (ArrayList<ConsoleDTO>) normalizedConsoles.stream()
                .map(ConsoleDTO::new)
                .collect(Collectors.toList());

        return consoleService.getConsolesByName(consoleDTOs).stream()
                .collect(Collectors.toMap(
                        console -> normalizeString(console.name()),
                        console -> console
                ));
    }

    private ConsoleReturnDTO handleConsoleCreationOrFetch(String consoleName, ConsoleReturnDTO console, String gameId) {
        if (console == null) {
            logger.info("Criando novo console '{}'", capitalizeEachWord(consoleName));
            console = consoleService.createConsole(new ConsoleDTO(capitalizeEachWord(consoleName)));
        } else {
            logger.info("Console '{}' já existe. Associando ao jogo ID: {}", console.name(), gameId);
        }
        return console;
    }

    private void addGameConsole(List<ConsoleReturnDTO> newGameConsoles, String gameId, ConsoleReturnDTO console) {
        var gameConsoleDTO = new GameConsoleDTO(gameId, console.id().toString());
        var gameConsoleCreated = gameConsoleService.createGameConsole(gameConsoleDTO);
        newGameConsoles.add(new ConsoleReturnDTO(gameConsoleCreated.consoleId(), gameConsoleCreated.consoleName()));
    }
}
