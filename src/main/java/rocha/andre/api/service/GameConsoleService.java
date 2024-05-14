package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.DTO.UpdateGameConsoleDTO;

public interface GameConsoleService {
    Page<GameConsoleReturnDTO> getAllGameConsolesByGameId(String gameId, Pageable pageable);
    GameConsoleReturnDTO createGameConsole(GameConsoleDTO data);
    Page<GameConsoleReturnDTO> updateGameConsoles(UpdateGameConsoleDTO data, String gameId);
}
