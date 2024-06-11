package rocha.andre.api.service;

import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    UserReturnDTO createUser(UserDTO data);
    UserReturnDTO getUserByTokenJWT(String id);
    TokenJwtDto performLogin(UserLoginDTO data);
    String forgotPassword(UserOnlyLoginDTO data);
    String resetPassword(UserResetPassDTO data);
    UserIdDTO getUserIdByJWT(String token);
    UserReturnDTO updateUserInfo(UserGetInfoUpdateDTO data, String tokenJWT);
    void deleteUser(String tokenJWT);
}
