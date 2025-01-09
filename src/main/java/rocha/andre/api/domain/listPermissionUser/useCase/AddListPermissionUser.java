package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserCreateDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.PermissionRepository;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class AddListPermissionUser {
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data) {
        if (data.participantLogin() == null || data.ownerId() == null || data.listId() == null || data.permissionId() == null) {
            throw new ValidationException("All fields are required in the process of adding permissions for a user on a list.");
        }

        var participantInvited = userRepository.findByLoginToHandle(data.participantLogin());
        if (participantInvited == null) {
            throw new ValidationException("No user was found with the provided login as a participant in the process of adding permissions for a user on a list.");
        }

        var ownerIdUUID = UUID.fromString(data.ownerId());
        var ownerList = userRepository.findById(ownerIdUUID)
                .orElseThrow(() -> new ValidationException("No user was found with the provided ID as the owner in the process of adding permissions for a user on a list."));

        var listAppIdUUID = UUID.fromString(data.listId());
        var listApp = listAppRepository.findById(listAppIdUUID)
                .orElseThrow(() -> new ValidationException("No list was found with the provided ID in the process of adding permissions for a user on a list."));

        var permissionIdUUID = UUID.fromString(data.permissionId());
        var permission = permissionRepository.findById(permissionIdUUID)
                .orElseThrow(() -> new ValidationException("No permission was found with the provided ID in the process of adding permissions for a user on a list."));

        var permissionAlreadyExists = listPermissionUserRepository.existsByParticipantIdAndListIdAndPermissionId(participantInvited.getId(), listAppIdUUID, permissionIdUUID);

        if (permissionAlreadyExists) {
            throw new ValidationException("There is already a permission for the specified list and user.");
        }

        var createDTO = new ListPermissionUserCreateDTO(listApp, permission, participantInvited, ownerList);

        var listPermissionUser = new ListPermissionUser(createDTO);

        final ListPermissionUser[] listPermissionUserOnDB = new ListPermissionUser[1];
        transactionTemplate.execute(status -> {
            try {
                listPermissionUserOnDB[0] = listPermissionUserRepository.save(listPermissionUser);
                createReadPermissionIfNotExists(participantInvited, listApp, ownerList);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("An error occurred during the transaction of adding a permission for a user on a list", e);
            }
            return null;
        });

        return new ListPermissionUserReturnDTO(listPermissionUserOnDB[0]);
    }

    public void createReadPermissionIfNotExists(User participantInvited, ListApp listApp, User owner) {
        var readEnum = (PermissionEnum.READ).toString();
        var permission = permissionRepository.findByPermissionName(readEnum);

        var existsReadPermission = listPermissionUserRepository.existsByParticipantIdAndListIdAndPermissionId(participantInvited.getId(), listApp.getId(), permission.getId());

        if (!existsReadPermission) {
            var createDTO = new ListPermissionUserCreateDTO(listApp, permission, participantInvited, owner);
            var listPermissionUserRead = new ListPermissionUser(createDTO);
            listPermissionUserRepository.save(listPermissionUserRead);
        }
    }
}
