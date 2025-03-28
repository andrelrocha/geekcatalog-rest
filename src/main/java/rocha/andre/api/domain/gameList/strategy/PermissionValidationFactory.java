package rocha.andre.api.domain.gameList.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.permission.PermissionEnum;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.user.User;

import java.util.UUID;

@Component
public class PermissionValidationFactory {
    @Autowired
    private AddGamePermissionValidation addGamePermissionValidation;
    @Autowired
    private DeleteGamePermissionValidation deleteGamePermissionValidation;
    @Autowired
    private UpdateGamePermissionValidation updateGamePermissionValidation;
    @Autowired
    private OwnerPermissionValidation ownerPermissionValidation;

    public PermissionValidationStrategy getStrategy(UserReturnDTO user, ListApp list, PermissionEnum permissionType) {
        var userIdUUID = UUID.fromString(user.id());
        if (userIdUUID.equals(list.getUser().getId())) {
            return ownerPermissionValidation;
        }

        // Retorna a estratégia correta com base na permissão
        return switch (permissionType) {
            case ADD_GAME -> addGamePermissionValidation;
            case DELETE_GAME -> deleteGamePermissionValidation;
            case UPDATE_GAME -> updateGamePermissionValidation;
            default -> throw new IllegalArgumentException("Invalid permission type: " + permissionType);
        };
    }
}
