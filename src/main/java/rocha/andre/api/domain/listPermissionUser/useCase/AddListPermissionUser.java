package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserCreateDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionRepository;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class AddListPermissionUser {
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    public ListPermissionUserReturnDTO addPermissionToUserOnList(ListPermissionUserDTO data) {
        if (data.participantLogin() == null || data.ownerId() == null || data.listId() == null || data.permissionId() == null) {
            throw new ValidationException("Todos os campos são obrigatórios no processo de adição de permissões a um usuário sobre uma lista.");
        }

        var participantInvited = userRepository.findByLoginToHandle(data.participantLogin());
        if (participantInvited == null) {
            throw new ValidationException("Não foi encontrado usuário com o login informado como participant no processo de adição de permissões a um usuário sobre uma lista");
        }

        var ownerIdUUID = UUID.fromString(data.ownerId());
        var ownerList = userRepository.findById(ownerIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado usuário com o id informado como owner no processo de adição de permissões a um usuário sobre uma lista"));

        var listAppIdUUID = UUID.fromString(data.listId());
        var listApp = listAppRepository.findById(listAppIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrada lista com o id informado no processo de adição de permissões a um usuário sobre uma lista"));

        var permissionIdUUID = UUID.fromString(data.permissionId());
        var permission = permissionRepository.findById(permissionIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrada permissão com o id informado no processo de adição de permissões a um usuário sobre uma lista"));

        var permissionAlreadyExists = listPermissionUserRepository.existsByParticipantIdAndListIdAndPermissionId(participantInvited.getId(), listAppIdUUID, permissionIdUUID);

        if (permissionAlreadyExists) {
            throw new ValidationException("Já existe uma permissão para a lista e o usuário informados");
        }

        var createDTO = new ListPermissionUserCreateDTO(listApp, permission, participantInvited, ownerList);

        var listPermissionUser = new ListPermissionUser(createDTO);

        var listPermissionUserOnDB = listPermissionUserRepository.save(listPermissionUser);

        return new ListPermissionUserReturnDTO(listPermissionUserOnDB);
    }
}
