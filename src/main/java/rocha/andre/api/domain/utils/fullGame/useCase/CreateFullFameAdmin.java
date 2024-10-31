package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.useCase.CreateGame;
import rocha.andre.api.domain.gameConsole.useCase.CreateGameConsole;
import rocha.andre.api.domain.gameGenre.useCase.CreateGameGenre;
import rocha.andre.api.domain.gameStudio.useCase.CreateGameStudio;

@Component
public class CreateFullFameAdmin {
    @Autowired
    private CreateGame createGame;
    /*
    @Autowired
    private CreateStudio createStudio;
    @Autowired
    private CreateConsole createConsole;
     */
    @Autowired
    private CreateGameGenre createGameGenre;
    @Autowired
    private CreateGameStudio createGameStudio;
    @Autowired
    private CreateGameConsole createGameConsole;

}
