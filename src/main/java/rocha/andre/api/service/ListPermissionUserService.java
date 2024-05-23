package rocha.andre.api.service;

import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;

public interface ListPermissionUserService {
    ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data);
}
