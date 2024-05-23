package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.useCase.AddListPermissionUser;
import rocha.andre.api.service.ListPermissionUserService;

@Service
public class ListPermissionUserServiceImpl implements ListPermissionUserService {
    @Autowired
    private AddListPermissionUser addListPermissionUser;

    @Override
    public ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data) {
        var listPermissionUser = addListPermissionUser.addPermissionToUserOnList(data);
        return listPermissionUser;
    }
}
