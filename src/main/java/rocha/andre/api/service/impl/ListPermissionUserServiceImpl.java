package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.useCase.AddListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.useCase.DeleteListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.useCase.GetListPermissionUserByListAndUser;
import rocha.andre.api.service.ListPermissionUserService;

import java.util.List;

@Service
public class ListPermissionUserServiceImpl implements ListPermissionUserService {
    @Autowired
    private AddListPermissionUser addListPermissionUser;
    @Autowired
    private DeleteListPermissionUser deleteListPermissionUser;
    @Autowired
    private GetListPermissionUserByListAndUser getListPermissionUserByListAndUser;

    @Override
    public ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data) {
        var listPermissionUser = addListPermissionUser.addPermissionToUserOnList(data);
        return listPermissionUser;
    }

    @Override
    public void deleteListPermission(ListPermissionUserDTO data) {
        deleteListPermissionUser.deleteListPermission(data);
    }

    @Override
    public List<ListPermissionUserReturnDTO> getAllPermissionsByUserAndListID(String tokenJWT, String listId) {
        var allPermissions = getListPermissionUserByListAndUser.getAllPermissionsByUserAndListID(tokenJWT, listId);
        return allPermissions;
    }
}
