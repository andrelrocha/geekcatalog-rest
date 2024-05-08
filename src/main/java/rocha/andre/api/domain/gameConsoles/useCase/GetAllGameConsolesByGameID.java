package rocha.andre.api.domain.gameConsoles.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsoles.GameConsoleRepository;

import java.util.UUID;

@Component
public class GetAllGameConsolesByGameID {
    @Autowired
    private GameConsoleRepository repository;

    public Page<GameConsoleReturnDTO> getAllGameConsolesByGameId(String gameId, Pageable pageable) {
        var gameIdUUID = UUID.fromString(gameId);

        var gameConsolesByGame = repository.findAllGameConsolesByGameId(gameIdUUID, pageable).map(GameConsoleReturnDTO::new);

        return gameConsolesByGame;
    }
}