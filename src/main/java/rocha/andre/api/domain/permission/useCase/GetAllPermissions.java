package rocha.andre.api.domain.permission.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.permission.DTO.PermissionReturnDTO;
import rocha.andre.api.domain.permission.PermissionRepository;

@Component
public class GetAllPermissions {
    @Autowired
    private PermissionRepository repository;

    public Page<PermissionReturnDTO> getAllPermissions(Pageable pageable) {
        var permissions = repository.findAllPermissionOrderedByName(pageable).map(PermissionReturnDTO::new);
        return permissions;
    }
}