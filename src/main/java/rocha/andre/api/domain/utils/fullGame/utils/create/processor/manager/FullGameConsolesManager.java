package rocha.andre.api.domain.utils.fullGame.utils.create.processor.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.utils.fullGame.utils.create.processor.ConsoleProcessor;

import java.util.ArrayList;
import java.util.List;

@Component
public class FullGameConsolesManager {
    @Autowired
    private ConsoleProcessor consoleProcessor;

    public ArrayList<ConsoleReturnDTO> manageFullGameConsoles(List<String> consoles, String gameId) {
        var normalizedConsolesWithSystemNomenclature = consoleProcessor.normalizeAndConvertNames(consoles);

        var consolesMapWithId = consoleProcessor.fetchConsolesWithId(normalizedConsolesWithSystemNomenclature);

        var newGameConsoles = new ArrayList<ConsoleReturnDTO>();

        for (String consoleName : normalizedConsolesWithSystemNomenclature) {
            ConsoleReturnDTO console = consolesMapWithId.getOrDefault(consoleName, null);

            console = consoleProcessor.handleConsoleCreationOrFetch(consoleName, console, gameId);

            consoleProcessor.addGameConsole(newGameConsoles, gameId, console);
        }

        return newGameConsoles;
    }
}
