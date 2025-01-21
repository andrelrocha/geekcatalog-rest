package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.useCase.GetAllConsolesByGameId;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameList.DTO.GameListGameAndConsolesDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetGameIdAndConsolesAvailableByGameListID {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GetAllConsolesByGameId getAllConsolesByGameId;

    public GameListGameAndConsolesDTO getGameInfoByGameListID(String gameListId) {
        var gameListIdUUID = UUID.fromString(gameListId);
        var gameList = gameListRepository.findById(gameListIdUUID)
                .orElseThrow(() -> new ValidationException("No game list was found for the provided id."));

        var game = gameRepository.findById(gameList.getGame().getId())
                .orElseThrow(() -> new ValidationException("No game was found for the provided id."));

        var consolesAvailable = getAllConsolesByGameId.getAllConsolesByGameId(null, String.valueOf(game.getId()));

        if (consolesAvailable.isEmpty()) {
            throw new RuntimeException("No available consoles were found for the provided game.");
        }

        return new GameListGameAndConsolesDTO(game.getId(), consolesAvailable.getContent());
    }
}
