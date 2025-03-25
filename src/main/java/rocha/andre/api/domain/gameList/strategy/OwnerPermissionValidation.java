package rocha.andre.api.domain.gameList.strategy;

import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

public class OwnerPermissionValidation implements PermissionValidationStrategy {
    @Override
    public void validate(User user, ListApp list, UUID userId) {
        if (!user.getId().equals(list.getUser().getId())) {
            throw new ValidationException("Only the list owner can add games.");
        }
    }
}
