package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionBulkAddDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserCreateDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AddBulkListPermissionUser {
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

    public ArrayList<ListPermissionUserReturnDTO> addBulkPermissionToUserOnList(ListPermissionBulkAddDTO data) {
        if (data.participantLogin() == null || data.ownerId() == null || data.listId() == null || data.permissionsId().isEmpty()) {
            throw new ValidationException("All fields are required in the process of adding permissions to a user on a list.");
        }

        var participantInvited = userRepository.findByLoginToHandle(data.participantLogin());
        if (participantInvited == null) {
            throw new ValidationException("No user was found with the provided login as participant in the process of adding permissions to a user on a list.");
        }

        var ownerIdUUID = UUID.fromString(data.ownerId());
        var ownerList = userRepository.findById(ownerIdUUID)
                .orElseThrow(() -> new ValidationException("No user was found with the provided ID as the owner in the process of adding permissions to a user on a list."));

        var listAppIdUUID = UUID.fromString(data.listId());
        var listApp = listAppRepository.findById(listAppIdUUID)
                .orElseThrow(() -> new ValidationException("No list was found with the provided ID in the process of adding permissions to a user on a list."));

        var permissionsUUID = data.permissionsId().stream()
                .map(UUID::fromString)
                .toList();

        var permissions = permissionsUUID.stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new ValidationException("No permission was found with the provided ID in the process of adding permissions to a user on a list.")))
                .toList();

        var existingPermissions = listPermissionUserRepository.findAllByParticipantIdAndListId(participantInvited.getId(), listAppIdUUID);

        var newPermissions = permissions.stream()
                .filter(permission -> existingPermissions.stream()
                        .noneMatch(existing -> existing.getPermission().getId().equals(permission.getId())))
                .toList();

        if (newPermissions.isEmpty()) {
            throw new ValidationException("All permissions already exist for the specified list and user.");
        }

        var createDTOs = newPermissions.stream()
                .map(permission -> new ListPermissionUserCreateDTO(listApp, permission, participantInvited, ownerList))
                .toList();

        var listPermissionUsers = createDTOs.stream()
                .map(ListPermissionUser::new)
                .collect(Collectors.toList());

        final List<ListPermissionUser>[] listPermissionUsersOnDB = new List[1];
        transactionTemplate.execute(status -> {
            try {
                listPermissionUsersOnDB[0] = listPermissionUserRepository.saveAll(listPermissionUsers);
                createReadPermissionIfNotExists(participantInvited, listApp, ownerList);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("An error occurred during the transaction of adding permissions to a user on a list", e);
            }
            return null;
        });

        var returnDTO = new ArrayList<ListPermissionUserReturnDTO>();

        listPermissionUsers.forEach(list -> {
            var listPermissionReturn = new ListPermissionUserReturnDTO(list);
            returnDTO.add(listPermissionReturn);
        });

        return returnDTO;
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
