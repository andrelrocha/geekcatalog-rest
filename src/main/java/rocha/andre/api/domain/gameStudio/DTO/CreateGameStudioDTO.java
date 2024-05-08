package rocha.andre.api.domain.gameStudio.DTO;

import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.studios.Studio;

public record CreateGameStudioDTO(Game game, Studio studio) {
}
