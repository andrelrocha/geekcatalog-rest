package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleGameIdDTO;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsoles.useCase.GetAllGameConsolesByGameID;
import rocha.andre.api.service.GameConsoleService;

@Service
public class GameConsoleServiceImpl implements GameConsoleService {
    @Autowired
    private GetAllGameConsolesByGameID getAllGameConsolesByGameID;

    @Override
    public Page<GameConsoleReturnDTO> getAllGameConsolesByGameId(String gameId, Pageable pageable) {
        var gameConsoles = getAllGameConsolesByGameID.getAllGameConsolesByGameId(gameId, pageable);
        return gameConsoles;
    }
}
