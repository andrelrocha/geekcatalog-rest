package rocha.andre.api.domain.permission.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.permission.DTO.PermissionReturnDTO;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.PermissionRepository;

@Component
public class GetPermissionByNameENUM {
    @Autowired
    private PermissionRepository repository;

    public PermissionReturnDTO getPermissionByNameOnENUM(PermissionEnum permission) {
        var permissionString = permission.toString();
        var permissionOnDB = repository.findByPermissionName(permissionString);
        return new PermissionReturnDTO(permissionOnDB);
    }
}
