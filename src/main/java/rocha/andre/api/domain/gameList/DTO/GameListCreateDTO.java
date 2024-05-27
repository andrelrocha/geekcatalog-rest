package rocha.andre.api.domain.gameList.DTO;

import rocha.andre.api.domain.consoles.Console;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.User;

public record GameListCreateDTO(User user, Game game, ListApp listApp, Console consolePlayed, String note) {
}
