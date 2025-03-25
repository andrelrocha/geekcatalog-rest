package rocha.andre.api.domain.gameList.strategy;

import rocha.andre.api.domain.gameList.DTO.GameListDTO;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

public interface PermissionValidationStrategy {
    void validate(User user, ListApp list, UUID userId) throws ValidationException;
}
