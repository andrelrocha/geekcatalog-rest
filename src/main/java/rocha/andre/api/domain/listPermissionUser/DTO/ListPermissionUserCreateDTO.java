package rocha.andre.api.domain.listPermissionUser.DTO;

import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.permission.Permission;
import rocha.andre.api.domain.user.User;

public record ListPermissionUserCreateDTO(ListApp list, Permission permission, User participant, User owner) {
}
