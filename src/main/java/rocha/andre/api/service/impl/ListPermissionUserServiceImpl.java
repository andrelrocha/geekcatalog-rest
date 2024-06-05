package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.listPermissionUser.DTO.DeleteListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionBulkAddDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.useCase.*;
import rocha.andre.api.service.ListPermissionUserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListPermissionUserServiceImpl implements ListPermissionUserService {
    @Autowired
    private AddListPermissionUser addListPermissionUser;
    @Autowired
    private AddBulkListPermissionUser addBulkListPermissionUser;
    @Autowired
    private DeleteListPermissionUser deleteListPermissionUser;
    @Autowired
    private DeleteAllListPermissionUser deleteAllListPermissionUser;
    @Autowired
    private GetListPermissionUserByListAndUser getListPermissionUserByListAndUser;

    @Override
    public ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data) {
        var listPermissionUser = addListPermissionUser.addPermissionToUserOnList(data);
        return listPermissionUser;
    }

    @Override
    public ArrayList<ListPermissionUserReturnDTO> addBulkPermissionToUserOnList(ListPermissionBulkAddDTO data) {
        var listPermissionsUser = addBulkListPermissionUser.addBulkPermissionToUserOnList(data);
        return listPermissionsUser;
    }

    @Override
    public void deleteListPermission(ListPermissionUserDTO data) {
        deleteListPermissionUser.deleteListPermission(data);
    }

    @Override
    public void deleteAllListPermission(DeleteListPermissionUserDTO data) {
        deleteAllListPermissionUser.deleteAllListPermission(data);
    }

    @Override
    public List<ListPermissionUserReturnDTO> getAllPermissionsByUserAndListID(String tokenJWT, String listId) {
        var allPermissions = getListPermissionUserByListAndUser.getAllPermissionsByUserAndListID(tokenJWT, listId);
        return allPermissions;
    }
}
