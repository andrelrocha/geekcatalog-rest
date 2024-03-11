package rocha.andre.api.service;

import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserDTO data);
    UserReturnDTO createUser(UserDTO data);
    UserForgotDTO forgotPassword(UserLoginDTO data);
    String resetPassword(UserResetPassDTO data);
}
