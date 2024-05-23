package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
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
                .orElseThrow(() -> new ValidationException("Não foi encontrada lista com o id informado no processo de delete da lista."));

        var user = getUserByTokenJWT.getUserByID(tokenJWT);
        var userIdUUID = UUID.fromString(user.id());

        var errorMessagePermission = "O usuário que está tentando apagar a lista não é o proprietário da mesma e nem tem permissão para a operação.";

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
                repository.delete(listApp);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Ocorreu um erro na transação de delete da lista e de suas permissões", e);
            }
            return null;
        });
    }
}
