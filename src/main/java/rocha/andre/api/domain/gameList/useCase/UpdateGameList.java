package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUpdateRequestDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class UpdateGameList {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private ConsoleRepository consoleRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;

    public GameListFullReturnDTO updateGameList(GameListUpdateRequestDTO data, String gameListId) {
        var gameListIdUUID = UUID.fromString(gameListId);
        var gameList = gameListRepository.findById(gameListIdUUID)
                .orElseThrow(() -> new ValidationException("No game found in the list with the provided id."));

        var userIdUUID = UUID.fromString(data.userId());

        var errorMessagePermission = "The user trying to update game information is not the owner of the list or does not have permission to do so.";
        if (!userIdUUID.equals(gameList.getList().getUser().getId())) {
            var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userIdUUID, gameList.getList().getId());
            if (!listsPermission.isEmpty()) {
                var updateGameEnum = PermissionEnum.UPDATE_GAME;
                var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(updateGameEnum);
                var userPermissionList = listPermissionUserRepository.findByParticipantIdAndListIdAndPermissionId(userIdUUID, gameList.getList().getId(), permission.id());

                if (userPermissionList == null) {
                    throw new ValidationException(errorMessagePermission);
                }
                if (!gameList.getList().getUser().getId().equals(userPermissionList.getOwner().getId())) {
                    throw new ValidationException(errorMessagePermission);
                }
            } else {
                throw new ValidationException(errorMessagePermission);
            }
        }


        var consoleIdUUID = UUID.fromString(data.consoleId());
        var console = consoleRepository.findById(consoleIdUUID)
                .orElseThrow(() -> new ValidationException("No console found with the provided id, in the update game list."));

        var updateDTO = new GameListUpdateDTO(console, data.note());

        gameList.updateGameList(updateDTO);

        var gameListUpdated = gameListRepository.save(gameList);

        return new GameListFullReturnDTO(gameListUpdated);
    }
}
