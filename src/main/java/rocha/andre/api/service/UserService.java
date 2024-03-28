package rocha.andre.api.service;

import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    UserReturnDTO createUser(UserDTO data);
    UserReturnDTO getUserByID(String id);
    TokenJwtDto performLogin(UserLoginDTO data);
    UserForgotDTO forgotPassword(UserOnlyLoginDTO data);
    String resetPassword(UserResetPassDTO data);
}
