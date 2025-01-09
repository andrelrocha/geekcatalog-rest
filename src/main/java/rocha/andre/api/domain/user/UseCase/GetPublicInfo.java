package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.user.DTO.UserPublicReturnDTO;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetPublicInfo {
    @Autowired
    private UserRepository repository;

    public UserPublicReturnDTO getPublicInfoByUserId(String userId) {
        var userIdUUID = UUID.fromString(userId);
        var user = repository.findById(userIdUUID)
                .orElseThrow(() -> new ValidationException("No User was found for the provided ID."));

        return new UserPublicReturnDTO(user.getName(), user.getBirthday(), user.getCountry().getName(), user.getCountry().getId());
    }
}
