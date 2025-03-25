package rocha.andre.api.domain.gameList.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.user.User;

@Component
public class PermissionValidationFactory {
    @Autowired
    private ParticipantPermissionValidation participantPermissionValidation;
    @Autowired
    private OwnerPermissionValidation ownerPermissionValidation;

    public PermissionValidationStrategy getStrategy(User user, ListApp list) {
        if (user.getId().equals(list.getUser().getId())) {
            return ownerPermissionValidation;
        }
        return participantPermissionValidation;
    }
}

