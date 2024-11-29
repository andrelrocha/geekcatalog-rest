package rocha.andre.api.domain.utils.fullGame.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.service.GameService;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class FullGameCreateEntityProcessor {
    @Autowired
    private GameService gameService;

    public GameReturnDTO processCreateGameEntity(CreateFullGameDTO data) {
        var gameDTO = new GameDTO(capitalizeEachWord(normalizeString(data.name())), data.metacritic(), data.yearOfRelease());
        return gameService.createGame(gameDTO);
    }
}
