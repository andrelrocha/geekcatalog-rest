package rocha.andre.api.domain.gameConsole.DTO;

import rocha.andre.api.domain.consoles.Console;
import rocha.andre.api.domain.game.Game;

public record CreateGameConsoleDTO(Game game, Console console) {
}
