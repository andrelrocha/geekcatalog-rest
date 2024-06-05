package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionBulkAddDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserCreateDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.PermissionRepository;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AddBulkListPermissionUser {
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public ArrayList<ListPermissionUserReturnDTO> addBulkPermissionToUserOnList(ListPermissionBulkAddDTO data) {
        if (data.participantLogin() == null || data.ownerId() == null || data.listId() == null || data.permissionsId().isEmpty()) {
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

        var permissionsUUID = data.permissionsId().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        var permissions = permissionsUUID.stream()
                .map(id -> permissionRepository.findById(id)
                        .orElseThrow(() -> new ValidationException("Não foi encontrada permissão com o id informado no processo de adição de permissões a um usuário sobre uma lista")))
                .collect(Collectors.toList());

        var existingPermissions = listPermissionUserRepository.findAllByParticipantIdAndListId(participantInvited.getId(), listAppIdUUID);

        var newPermissions = permissions.stream()
                .filter(permission -> existingPermissions.stream()
                        .noneMatch(existing -> existing.getPermission().getId().equals(permission.getId())))
                .collect(Collectors.toList());

        if (newPermissions.isEmpty()) {
            throw new ValidationException("Já existem todas as permissões para a lista e o usuário informados");
        }

        var createDTOs = newPermissions.stream()
                .map(permission -> new ListPermissionUserCreateDTO(listApp, permission, participantInvited, ownerList))
                .collect(Collectors.toList());

        var listPermissionUsers = createDTOs.stream()
                .map(ListPermissionUser::new)
                .collect(Collectors.toList());

        final List<ListPermissionUser>[] listPermissionUsersOnDB = new List[1];
        transactionTemplate.execute(status -> {
            try {
                listPermissionUsersOnDB[0] = listPermissionUserRepository.saveAll(listPermissionUsers);
                createReadPermissionIfNotExists(participantInvited, listApp, ownerList);
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException("Ocorreu um erro na transação de adição de permissões para um usuário sobre uma lista", e);
            }
            return null;
        });

        var returnDTO = new ArrayList<ListPermissionUserReturnDTO>();

        listPermissionUsers.forEach(list -> {
            var listPermissionReturn = new ListPermissionUserReturnDTO(list);
            returnDTO.add(listPermissionReturn);
        });

        return returnDTO;
    }

    public void createReadPermissionIfNotExists(User participantInvited, ListApp listApp, User owner) {
        var readEnum = (PermissionEnum.READ).toString();
        var permission = permissionRepository.findByPermissionName(readEnum);

        var existsReadPermission = listPermissionUserRepository.existsByParticipantIdAndListIdAndPermissionId(participantInvited.getId(), listApp.getId(), permission.getId());

        if (!existsReadPermission) {
            var createDTO = new ListPermissionUserCreateDTO(listApp, permission, participantInvited, owner);
            var listPermissionUserRead = new ListPermissionUser(createDTO);
            listPermissionUserRepository.save(listPermissionUserRead);
        }
    }
}
