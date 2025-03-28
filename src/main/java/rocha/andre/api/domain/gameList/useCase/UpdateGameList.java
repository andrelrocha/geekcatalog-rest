package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateRequestDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameList.auxService.GameListEntityExtractorServiceImpl;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationFactory;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationStrategy;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;


@Component
public class UpdateGameList {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameListEntityExtractorServiceImpl gameListEntityExtractorServiceImpl;

    @Autowired
    private PermissionValidationFactory permissionValidationFactory;

    public GameListFullReturnDTO updateGameList(GameListUpdateRequestDTO data, String gameListId) {
        var gameList = gameListEntityExtractorServiceImpl.extractGameList(gameListId);

        var userReturnDTO = new UserReturnDTO(gameList.getList().getUser());
        PermissionValidationStrategy strategy = permissionValidationFactory.getStrategy(userReturnDTO, gameList.getList(), PermissionEnum.UPDATE_GAME);
        strategy.validate(userReturnDTO, gameList.getList());

        var console = gameListEntityExtractorServiceImpl.extractConsole(data.consoleId(), gameList.getGame().getId().toString());

        var updateDTO = new GameListUpdateDTO(console, data.note());
        gameList.updateGameList(updateDTO);

        var gameListUpdated = gameListRepository.save(gameList);

        return new GameListFullReturnDTO(gameListUpdated);
    }
}
