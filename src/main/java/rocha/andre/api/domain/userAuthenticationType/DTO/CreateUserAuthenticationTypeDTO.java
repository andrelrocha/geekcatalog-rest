package rocha.andre.api.domain.userAuthenticationType.DTO;

import rocha.andre.api.domain.authenticationType.AuthenticationType;
import rocha.andre.api.domain.user.User;

public record CreateUserAuthenticationTypeDTO(AuthenticationType authenticationType, User user, String OAuthId) {
}
