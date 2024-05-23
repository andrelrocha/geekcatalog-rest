package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class UpdateList {
    @Autowired
    private ListAppRepository repository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;

    public ListAppReturnDTO updateListApp(ListAppDTO data, String listId) {
        var listIdUUID = UUID.fromString(listId);
        var userIdUUID = UUID.fromString(data.userId());

        var listApp = repository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrada lista com o id informado no processo de update da lista"));

        var errorMessagePermission = "O usuário que está tentando editar a lista não é o proprietário da mesma e nem tem permissão para a operação.";

        if (!listApp.getUser().getId().equals(userIdUUID)) {
            var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userIdUUID, listApp.getId());
            if (!listsPermission.isEmpty()) {
                var updatedEnum = PermissionEnum.UPDATE;
                var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(updatedEnum);
                var userPermissionList = listPermissionUserRepository.findByParticipantIdAndListIdAndPermissionId(userIdUUID, listApp.getId(), permission.id());

                if (!listApp.getUser().getId().equals(userPermissionList.getOwner().getId())) {
                    throw new ValidationException(errorMessagePermission);
                }
            } else {
                throw new ValidationException(errorMessagePermission);
            }
        }

        listApp.updateList(data);

        var listAppOnDB = repository.save(listApp);

        return new ListAppReturnDTO(listAppOnDB);
    }
}
