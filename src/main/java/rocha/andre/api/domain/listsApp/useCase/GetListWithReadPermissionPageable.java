package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.PermissionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class GetListWithReadPermissionPageable {
    @Autowired
    private ListAppRepository listsAppRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;

    public Page<ListAppReturnDTO> getAllListsWithReadPermission(String userId, Pageable pageable) {
        var userIdUUID = UUID.fromString(userId);

        try {
            var readEnum = (PermissionEnum.READ).toString();
            var readPermission = permissionRepository.findByPermissionName(readEnum);
            var listsPermissionWithReadPermissionToUser = listPermissionUserRepository.findAllByParticipantIdAndPermissionId(userIdUUID, readPermission.getId());
            List<UUID> listsAppID = new ArrayList<>();
            for (ListPermissionUser listPermission : listsPermissionWithReadPermissionToUser) {
                listsAppID.add(listPermission.getList().getId());
            }
            var listsWithReadPermissionToUser = listsAppRepository.findAllListsAppById(listsAppID, pageable).map(ListAppReturnDTO::new);
            return listsWithReadPermissionToUser;
        } catch (Exception e) {
            System.err.println("Erro ao obter listas com permissão de leitura: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
