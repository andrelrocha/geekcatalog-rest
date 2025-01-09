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
                .orElseThrow(() -> new ValidationException("No list was found with the provided ID during the update process."));

        var errorMessagePermission = "The user attempting to edit the list is neither the owner nor has the required permission for this operation.";

        if (!listApp.getUser().getId().equals(userIdUUID)) {
            var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userIdUUID, listApp.getId());
            if (!listsPermission.isEmpty()) {
                var updatedEnum = PermissionEnum.UPDATE;
                var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(updatedEnum);
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

        listApp.updateList(data);

        var listAppOnDB = repository.save(listApp);

        return new ListAppReturnDTO(listAppOnDB);
    }
}
