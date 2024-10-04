package rocha.andre.api.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.AuthTokensDTO;

public interface UserService {
    UserReturnDTO createUser(UserDTO data);
    UserReturnDTO getUserByTokenJWT(String id);
    AuthTokensDTO performLogin(UserLoginDTO data, HttpServletRequest request);
    String forgotPassword(UserOnlyLoginDTO data);
    String resetPassword(UserResetPassDTO data);
    UserIdDTO getUserIdByJWT(String token);
    UserReturnDTO updateUserInfo(UserGetInfoUpdateDTO data, String tokenJWT);
    void deleteUser(String tokenJWT);
    UserPublicReturnDTO getPublicInfoByUserId(String userId);
}
