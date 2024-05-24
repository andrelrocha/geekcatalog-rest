package rocha.andre.api.service;

import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;

import java.util.List;

public interface ListPermissionUserService {
    ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data);
    void deleteListPermission(ListPermissionUserDTO data);
    void deleteAllListPermission(ListPermissionUserDTO data);
    List<ListPermissionUserReturnDTO> getAllPermissionsByUserAndListID(String tokenJWT, String listId);
}
