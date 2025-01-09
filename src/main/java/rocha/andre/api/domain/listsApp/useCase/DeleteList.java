package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteList {
    @Autowired
    private ListAppRepository repository;
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public void deleteList(String listId, String tokenJWT) {
        var listIdUUID = UUID.fromString(listId);
        var listApp = repository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("No List was found for the provided id while deleting it."));

        var user = getUserByTokenJWT.getUserByID(tokenJWT);
        var userIdUUID = UUID.fromString(user.id());

        var errorMessagePermission = "The user attempting to edit the list is neither the owner nor has the required permission for this operation.";

        if (!listApp.getUser().getId().equals(userIdUUID)) {
            var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userIdUUID, listApp.getId());
            if (!listsPermission.isEmpty()) {
                var deleteEnum = PermissionEnum.DELETE;
                var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(deleteEnum);
                var userPermissionList = listPermissionUserRepository.findByParticipantIdAndListIdAndPermissionId(userIdUUID, listApp.getId(), permission.id());

                if (userPermissionList == null) {
                    throw new ValidationException(errorMessagePermission);
                }
                if (!listApp.getUser().getId().equals(userPermissionList.getOwner().getId())) {
                    throw new ValidationException(errorMessagePermission);
                }
            } else {
                throw new ValidationException(errorMessagePermission);
            }
        }

        transactionTemplate.execute(status -> {
            try {
                var permissionsToDelete = listPermissionUserRepository.findAllByListId(listIdUUID);
                listPermissionUserRepository.deleteAll(permissionsToDelete);
                var gameListToDelete = gameListRepository.findAllByListId(listIdUUID);
                gameListRepository.deleteAll(gameListToDelete);
                repository.delete(listApp);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("An error occurred during the delete transaction of the list and its permissions: ", e);
            }
            return null;
        });
    }
}
