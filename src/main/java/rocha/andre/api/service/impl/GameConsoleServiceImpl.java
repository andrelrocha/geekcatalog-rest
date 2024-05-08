package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.useCase.CreateGameConsole;
import rocha.andre.api.domain.gameConsole.useCase.GetAllGameConsolesByGameID;
import rocha.andre.api.service.GameConsoleService;

@Service
public class GameConsoleServiceImpl implements GameConsoleService {
    @Autowired
    private CreateGameConsole createGameConsole;
    @Autowired
    private GetAllGameConsolesByGameID getAllGameConsolesByGameID;

    @Override
    public Page<GameConsoleReturnDTO> getAllGameConsolesByGameId(String gameId, Pageable pageable) {
        var gameConsoles = getAllGameConsolesByGameID.getAllGameConsolesByGameId(gameId, pageable);
        return gameConsoles;
    }

    @Override
    public GameConsoleReturnDTO createGameConsole(GameConsoleDTO data) {
        var newGameConsole = createGameConsole.createGameConsole(data);
        return newGameConsole;
    }
}
