package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleReturnDTO;

public interface GameConsoleService {
    Page<GameConsoleReturnDTO> getAllGameConsolesByGameId(String gameId, Pageable pageable);
    GameConsoleReturnDTO createGameConsole(GameConsoleDTO data);
}
