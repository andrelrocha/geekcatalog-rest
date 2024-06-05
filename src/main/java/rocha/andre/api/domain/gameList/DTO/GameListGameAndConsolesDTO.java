package rocha.andre.api.domain.gameList.DTO;

import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;

import java.util.List;
import java.util.UUID;

public record GameListGameAndConsolesDTO(UUID gameId, List<ConsoleReturnDTO> consolesAvailable) {
}
