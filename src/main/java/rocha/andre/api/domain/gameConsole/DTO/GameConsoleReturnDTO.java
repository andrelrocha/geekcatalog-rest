package rocha.andre.api.domain.gameConsole.DTO;

import rocha.andre.api.domain.gameConsole.GameConsole;

import java.util.UUID;

public record GameConsoleReturnDTO(UUID id, String gameName, String consoleName) {
    public GameConsoleReturnDTO(GameConsole gameConsole) {
        this(gameConsole.getId(), gameConsole.getGame().getName(), gameConsole.getConsole().getName());
    }
}
