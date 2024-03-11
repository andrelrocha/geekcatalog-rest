package rocha.andre.api.service;

import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserLoginDTO data);
    UserReturnDTO createUser(UserDTO data);
    UserForgotDTO forgotPassword(UserOnlyLoginDTO data);
    String resetPassword(UserResetPassDTO data);
}
