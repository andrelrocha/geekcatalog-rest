package rocha.andre.api.domain.listPermissionUser.DTO;

import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;

import java.util.UUID;

public record ListPermissionUserReturnDTO(UUID id, UUID listId, UUID permissionId, UUID participantId, UUID ownerId) {
    public ListPermissionUserReturnDTO(ListPermissionUser listPermissionUser) {
        this(listPermissionUser.getId(), listPermissionUser.getList().getId(), listPermissionUser.getPermission().getId(),
                listPermissionUser.getParticipant().getId(), listPermissionUser.getOwner().getId());
    }
}
