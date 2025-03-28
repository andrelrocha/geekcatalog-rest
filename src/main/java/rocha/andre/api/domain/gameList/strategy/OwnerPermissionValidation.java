package rocha.andre.api.domain.gameList.strategy;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;

@Component
public class OwnerPermissionValidation implements PermissionValidationStrategy {
    @Override
    public void validate(UserReturnDTO user, ListApp list) {
        // ele já é o dono da lista, ele faz o que quiser
    }
}
