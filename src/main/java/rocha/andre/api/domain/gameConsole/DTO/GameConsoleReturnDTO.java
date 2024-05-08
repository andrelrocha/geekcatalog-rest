package rocha.andre.api.domain.gameConsole.DTO;

import rocha.andre.api.domain.gameConsole.GameConsole;

public record GameConsoleReturnDTO(String gameName, String consoleName) {
    public GameConsoleReturnDTO(GameConsole gameConsole) {
        this(gameConsole.getGame().getName(), gameConsole.getConsole().getName());
    }
}
