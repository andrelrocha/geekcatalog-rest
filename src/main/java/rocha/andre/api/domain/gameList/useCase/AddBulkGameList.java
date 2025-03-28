package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.gameList.DTO.GameListBulkCreateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListBulkReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListCreateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.GameList;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.gameList.strategy.AddGamePermissionValidation;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationFactory;
import rocha.andre.api.domain.gameList.strategy.PermissionValidationStrategy;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AddBulkGameList {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;
    @Autowired
    private PermissionValidationFactory permissionValidationFactory;

    public ArrayList<GameListBulkReturnDTO> addBulkGamesToList(GameListBulkCreateDTO data) {
        var userIdUUID = UUID.fromString(data.userId());
        var user = userRepository.findById(userIdUUID)
                .orElseThrow(() -> new ValidationException("No user was found with the provided id when adding games to the bulk game list."));

        var listIdUUID = UUID.fromString(data.listId());
        var list = listAppRepository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("No list was found with the provided id when adding games to the bulk game list"));

        var userReturnDTO = new UserReturnDTO(user);
        PermissionValidationStrategy strategy = permissionValidationFactory.getStrategy(userReturnDTO, list, PermissionEnum.ADD_GAME);
        strategy.validate(userReturnDTO, list);

        var gameListCreate = new ArrayList<GameList>();
        for (String gameId : data.gamesId()) {
            var gameIdUUID = UUID.fromString(gameId);
            var game = gameRepository.findById(gameIdUUID)
                    .orElseThrow(() -> new ValidationException("No game was found with the provided id when adding games to the bulk game list."));

            if (gameListRepository.existsByGameIdAndListId(game.getId(), list.getId())) {
                continue;
            }

            var gameListCreateDTO = new GameListCreateDTO(user, game, list, null, null);
            gameListCreate.add(new GameList(gameListCreateDTO));
        }

        var gamesOnDB = gameListRepository.saveAll(gameListCreate);

        var gameListReturnDTOs = new ArrayList<GameListBulkReturnDTO>();
        for (GameList gameList : gamesOnDB) {
            gameListReturnDTOs.add(new GameListBulkReturnDTO(gameList));
        }

        return gameListReturnDTOs;
    }
}