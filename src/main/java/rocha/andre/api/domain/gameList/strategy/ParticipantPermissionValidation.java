package rocha.andre.api.domain.gameList.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.permission.useCase.GetPermissionByNameENUM;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ParticipantPermissionValidation implements PermissionValidationStrategy {
    @Autowired
    private ListPermissionUserRepository listPermissionUserRepository;
    @Autowired
    private GetPermissionByNameENUM getPermissionByNameENUM;

    @Override
    public void validate(User user, ListApp list, UUID userId) {
        var listsPermission = listPermissionUserRepository.findAllByParticipantIdAndListId(userId, list.getId());
        if (listsPermission.isEmpty()) {
            throw new ValidationException("The user does not have permission to add games.");
        }

        var addGameEnum = PermissionEnum.ADD_GAME;
        var permission = getPermissionByNameENUM.getPermissionByNameOnENUM(addGameEnum);
        var userPermissionList = listPermissionUserRepository.findByParticipantIdAndListIdAndPermissionId(user.getId(), list.getId(), permission.id());

        if (userPermissionList == null || !list.getUser().getId().equals(userPermissionList.getOwner().getId())) {
            throw new ValidationException("The user does not have permission to add games.");
        }
    }
}
