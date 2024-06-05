package rocha.andre.api.service;

import rocha.andre.api.domain.listPermissionUser.DTO.DeleteListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionBulkAddDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;

import java.util.ArrayList;
import java.util.List;

public interface ListPermissionUserService {
    ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data);
    ArrayList<ListPermissionUserReturnDTO> addBulkPermissionToUserOnList(ListPermissionBulkAddDTO data);
    void deleteListPermission(ListPermissionUserDTO data);
    void deleteAllListPermission(DeleteListPermissionUserDTO data);
    List<ListPermissionUserReturnDTO> getAllPermissionsByUserAndListID(String tokenJWT, String listId);
}
