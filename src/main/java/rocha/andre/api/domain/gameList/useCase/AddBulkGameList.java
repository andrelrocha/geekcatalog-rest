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
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
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

    public ArrayList<GameListBulkReturnDTO> addBulkGamesToList(GameListBulkCreateDTO data) {
        var userIdUUID = UUID.fromString(data.userId());
        var user = userRepository.findById(userIdUUID)
                .orElseThrow(()-> new ValidationException("No user was found with the provided id when adding games to the bulk game list."));

        var listIdUUID = UUID.fromString(data.listId());
        var list = listAppRepository.findById(listIdUUID)
                .orElseThrow(()-> new ValidationException("No list was found with the provided id when adding games to the bulk game list"));

        var errorMessagePermission = "The user attempting to add games is not the owner of the list or does not have permission to do so.";

        if (!user.getId().equals(list.getUser().getId())) {
            var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userIdUUID, list.getId());
            if (!listsPermission.isEmpty()) {
                var addGameEnum = PermissionEnum.ADD_GAME;
                var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(addGameEnum);
                var userPermissionList = listPermissionUserRepository.findByParticipantIdAndListIdAndPermissionId(user.getId(), list.getId(), permission.id());

                if (userPermissionList == null) {
                    throw new ValidationException(errorMessagePermission);
                }
                if (!list.getUser().getId().equals(userPermissionList.getOwner().getId())) {
                    throw new ValidationException(errorMessagePermission);
                }
            } else {
                throw new ValidationException(errorMessagePermission);
            }
        }

        var gameListCreate = new ArrayList<GameList>();
        for (String gameId : data.gamesId()) {
            var gameIdUUID = UUID.fromString(gameId);
            var game = gameRepository.findById(gameIdUUID)
                    .orElseThrow(()-> new ValidationException("No game was found with the provided id when adding games to the bulk game list."));

            var gameAlreadyExists = gameListRepository.existsByGameIdAndListId(game.getId(), list.getId());

            if (gameAlreadyExists) {
                continue;
            }

            var gameListCreateDTO = new GameListCreateDTO(user, game, list, null, null);
            var gameList = new GameList(gameListCreateDTO);
            gameListCreate.add(gameList);
        }

        var gamesOnDB = gameListRepository.saveAll(gameListCreate);

        var gameListReturnDTOs = new ArrayList<GameListBulkReturnDTO>();
        for (GameList gameList : gamesOnDB) {
            gameListReturnDTOs.add(new GameListBulkReturnDTO(gameList));
        }

        return gameListReturnDTOs;
    }
}
