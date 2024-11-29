package rocha.andre.api.domain.utils.fullGame.utils.processor;

import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;

import java.util.List;
import java.util.Map;

public interface ConsoleProcessor {
    void addGameConsole(List<ConsoleReturnDTO> newGameConsoles, String gameId, ConsoleReturnDTO console);
    Map<String, ConsoleReturnDTO> fetchConsolesWithId(List<String> normalizedConsoles);
    ConsoleReturnDTO handleConsoleCreationOrFetch(String consoleName, ConsoleReturnDTO console, String gameId);
    List<String> normalizeAndConvertNames(List<String> consoles);
}
