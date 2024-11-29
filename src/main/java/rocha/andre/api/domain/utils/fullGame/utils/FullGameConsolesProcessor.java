package rocha.andre.api.domain.utils.fullGame.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.infra.utils.stringFormatter.StringFormatter;
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
    private GameConsoleService gameConsoleService;

    private static final Logger logger = LoggerFactory.getLogger(FullGameConsolesProcessor.class);

    public ArrayList<ConsoleReturnDTO> processFullGameConsoles(List<String> consoles, String gameId) {
        var newGameConsoles = new ArrayList<ConsoleReturnDTO>();

        List <String> consolesWithSystemNomenclature = convertConsoles(consoles);
        List<String> normalizedConsoles = consolesWithSystemNomenclature.stream()
                .map(StringFormatter::normalizeString)
                .toList();

        Map<String, ConsoleReturnDTO> normalizedConsolesWithId = consoleService.getConsolesByName(
                new ArrayList<>(normalizedConsoles.stream()
                        .map(ConsoleDTO::new)
                        .toList())
        ).stream().collect(Collectors.toMap(
                console -> console.name().toLowerCase().trim(),
                console -> console
        ));
        for (String consoleName : normalizedConsoles) {
            ConsoleReturnDTO console = normalizedConsolesWithId.getOrDefault(consoleName, null);

            if (console == null) {
                logger.info("Criando novo console '{}'", capitalizeEachWord(consoleName));
                console = consoleService.createConsole(new ConsoleDTO(capitalizeEachWord(consoleName)));
            } else {
                logger.info("Console '{}' já existe. Associando ao jogo ID: {}", console.name(), gameId);
            }

            var gameConsoleDTO = new GameConsoleDTO(gameId, console.id().toString());
            var gameConsoleCreated = gameConsoleService.createGameConsole(gameConsoleDTO);

            newGameConsoles.add(new ConsoleReturnDTO(gameConsoleCreated.consoleId(), gameConsoleCreated.consoleName()));
        }

        return newGameConsoles;
    }

    private List<String> convertConsoles(List<String> consoles) {
        List<String> normalizedConsoles = new ArrayList<>();

        for (String console : consoles) {
            var normalizedConsole = normalizeString(console);

            if (normalizedConsole.startsWith("pc")) {
                normalizedConsole = "pc";
            }

            if (normalizedConsole.equals("xbox series x|s")) {
                normalizedConsoles.add("Xbox Series X");
                normalizedConsoles.add("Xbox Series S");
            } else {
                normalizedConsoles.add(normalizedConsole);
            }
        }

        return normalizedConsoles;
    }
}
