package rocha.andre.api.domain.gameConsoles.DTO;

import rocha.andre.api.domain.gameConsoles.GameConsole;

public record GameConsoleReturnDTO(String gameName, String consoleName) {
    public GameConsoleReturnDTO(GameConsole gameConsole) {
        this(gameConsole.getGame().getName(), gameConsole.getConsole().getName());
    }
}
