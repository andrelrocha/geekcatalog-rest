package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.*;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationFactory;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationStrategy;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.gameList.auxService.GameListEntityExtractorServiceImpl;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class AddGameList {

    @Autowired
    private GameListRepository gameListRepository;

    @Autowired
    private GameListEntityExtractorServiceImpl gameListEntityExtractorServiceImpl;

    @Autowired
    private PermissionValidationFactory permissionValidationFactory;

    public GameListFullReturnDTO addGameList(GameListDTO data) {
        var user = gameListEntityExtractorServiceImpl.extractUser(data.userId());
        var list = gameListEntityExtractorServiceImpl.extractList(data.listId());

        PermissionValidationStrategy strategy = permissionValidationFactory.getStrategy(new UserReturnDTO(user), list, PermissionEnum.ADD_GAME);
        strategy.validate(new UserReturnDTO(user), list);

        var game = gameListEntityExtractorServiceImpl.extractGame(data.gameId());

        if (gameListRepository.existsByGameIdAndListId(game.getId(), list.getId())) {
            throw new ValidationException("This game has already been added to the list.");
        }

        var console = gameListEntityExtractorServiceImpl.extractConsole(data.consoleId(), data.gameId());

        var gameListCreateDTO = new GameListCreateDTO(user, game, list, console, data.note());
        var gameList = new GameList(gameListCreateDTO);

        var gameListOnDB = gameListRepository.save(gameList);

        return new GameListFullReturnDTO(gameListOnDB);
    }
}
