package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionRepository;
import rocha.andre.api.domain.permission.useCase.GetAllPermissions;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GetListPermissionUserByListAndUser {
    @Autowired
    private ListPermissionUserRepository repository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;
    @Autowired
    private PermissionRepository permissionRepository;

    public List<ListPermissionUserReturnDTO> getAllPermissionsByUserAndListID(String tokenJWT, String listId) {
        var participant = getUserByTokenJWT.getUserByID(tokenJWT);
        var participantIdUUID = UUID.fromString(participant.id());
        var listIdUUID = UUID.fromString(listId);
        var list = listAppRepository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("No list was found for the provided ID during the permissions search."));

        if (list.getUser().getId().equals(participantIdUUID)) {
            var listPermission = new ArrayList<ListPermissionUserReturnDTO>();
            var permissions = permissionRepository.findAllPermissions();
            permissions.forEach(permission -> {
                var dto = new ListPermissionUserReturnDTO(null, listIdUUID, permission.getId(), permission.getPermission(), participantIdUUID, participant.name(), participantIdUUID);
                listPermission.add(dto);
            });
            return listPermission;
        }

        var allPermissionsByListAndUserID = repository.findAllByParticipantIdAndListId(participantIdUUID, listIdUUID)
                .stream()
                .map(ListPermissionUserReturnDTO::new)
                .collect(Collectors.toList());

        return allPermissionsByListAndUserID;
    }

}
