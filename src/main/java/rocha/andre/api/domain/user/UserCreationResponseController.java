package rocha.andre.api.domain.user;

import rocha.andre.api.domain.user.DTO.UserReturnDTO;

public record UserCreationResponseController(UserReturnDTO newUser, String message) {
}
