package rocha.andre.api.domain.gameList.DTO;

import rocha.andre.api.domain.consoles.Console;

public record GameListUpdateDTO(Console console, String note) {
}
