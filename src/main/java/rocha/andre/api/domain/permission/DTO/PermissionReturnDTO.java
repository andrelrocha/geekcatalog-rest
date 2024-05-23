package rocha.andre.api.domain.permission.DTO;

import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.permission.Permission;

import java.util.UUID;

public record PermissionReturnDTO(UUID id, String permission) {
    public PermissionReturnDTO(Permission permission) {
        this(permission.getId(), permission.getPermission());
    }
}