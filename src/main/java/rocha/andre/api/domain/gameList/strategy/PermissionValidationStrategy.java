package rocha.andre.api.domain.gameList.strategy;

import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

public interface PermissionValidationStrategy {
    void validate(UserReturnDTO user, ListApp list) throws ValidationException;
}
