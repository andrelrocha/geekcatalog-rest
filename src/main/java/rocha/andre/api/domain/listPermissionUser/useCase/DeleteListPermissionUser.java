package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.DTO.DeleteListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.PermissionRepository;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteListPermissionUser {
    @Autowired
    private ListPermissionUserRepository repository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private DeleteAllListPermissionUser deleteAllListPermissionUser;
    @Autowired
    private UserRepository userRepository;

    public void deleteListPermission(ListPermissionUserDTO data) {
        var participantInvited = userRepository.findByLoginToHandle(data.participantLogin());
        if (participantInvited == null) {
            throw new ValidationException("No user was found with the provided login as a participant in the process of adding (deleting) permissions for a user on a list.");
        }

        var listIdUUID = UUID.fromString(data.listId());
        var permissionIdUUID = UUID.fromString(data.permissionId());

        try {
            var readEnum = PermissionEnum.READ.toString();
            var permissionRead = permissionRepository.findByPermissionName(readEnum);
            if (permissionRead.getId().equals(permissionIdUUID)) {
                var deleteDTO = new DeleteListPermissionUserDTO(data.listId(), participantInvited.getLogin(), data.ownerId());
                deleteAllListPermissionUser.deleteAllListPermission(deleteDTO);
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong in the permission reading process during the delete listPermission use case.");
        }

        var ownerIdUUID = UUID.fromString(data.ownerId());
        var owner = userRepository.findById(ownerIdUUID)
                .orElseThrow(() -> new ValidationException("No user was found with the provided ID as the owner in the delete list permission process."));

        var listPermissionUser = repository.findByParticipantIdAndListIdAndPermissionId(participantInvited.getId(), listIdUUID, permissionIdUUID);

        if (listPermissionUser == null) {
            throw new ValidationException("No permission records were found for the user on the specified list.");
        } else if (!listPermissionUser.getOwner().equals(owner)) {
            throw new ValidationException("The user trying to delete a permission is not the owner of the list and does not have permission for the operation.");
        }

        repository.delete(listPermissionUser);
    }
}
