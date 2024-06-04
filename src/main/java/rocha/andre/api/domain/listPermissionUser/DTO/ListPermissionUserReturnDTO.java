package rocha.andre.api.domain.listPermissionUser.DTO;

import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;

import java.util.UUID;

public record ListPermissionUserReturnDTO(UUID id, UUID listId, UUID permissionId, String permissionName, UUID participantId, String participantName, UUID ownerId) {
    public ListPermissionUserReturnDTO(ListPermissionUser listPermissionUser) {
        this(listPermissionUser.getId(), listPermissionUser.getList().getId(), listPermissionUser.getPermission().getId(), listPermissionUser.getPermission().getPermission(),
                listPermissionUser.getParticipant().getId(), listPermissionUser.getParticipant().getName(), listPermissionUser.getOwner().getId());
    }
}
