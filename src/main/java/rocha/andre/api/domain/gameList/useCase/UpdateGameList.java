package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateRequestDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationFactory;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationStrategy;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class UpdateGameList {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private ConsoleRepository consoleRepository;
    @Autowired
    private PermissionValidationFactory permissionValidationFactory;

    public GameListFullReturnDTO updateGameList(GameListUpdateRequestDTO data, String gameListId) {
        var gameListIdUUID = UUID.fromString(gameListId);
        var gameList = gameListRepository.findById(gameListIdUUID)
                .orElseThrow(() -> new ValidationException("No game found in the list with the provided id."));

        var userReturnDTO = new UserReturnDTO(gameList.getList().getUser());
        PermissionValidationStrategy strategy = permissionValidationFactory.getStrategy(userReturnDTO, gameList.getList(), PermissionEnum.UPDATE_GAME);
        strategy.validate(userReturnDTO, gameList.getList());

        var consoleIdUUID = UUID.fromString(data.consoleId());
        var console = consoleRepository.findById(consoleIdUUID)
                .orElseThrow(() -> new ValidationException("No console found with the provided id, in the update game list."));

        var updateDTO = new GameListUpdateDTO(console, data.note());
        gameList.updateGameList(updateDTO);
        var gameListUpdated = gameListRepository.save(gameList);

        return new GameListFullReturnDTO(gameListUpdated);
    }
}
