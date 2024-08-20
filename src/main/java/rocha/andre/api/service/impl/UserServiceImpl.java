package rocha.andre.api.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.domain.user.UseCase.*;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private CreateUser createUser;
    @Autowired
    private DeleteUser deleteUser;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;
    @Autowired
    private GetUserIdByJWT getUserIdByJWT;
    @Autowired
    private GetPublicInfo getPublicInfo;
    @Autowired
    private ForgotPassword forgotPassword;
    @Autowired
    private PerformLogin performLogin;
    @Autowired
    private UpdateUser updateUser;
    @Autowired
    private ResetPassword resetPassword;

    @Override
    public TokenJwtDto performLogin(UserLoginDTO data) {
        var tokenJwt = performLogin.performLogin(data);
        return tokenJwt;
    }

    @Override
    public UserReturnDTO createUser(UserDTO data) {
        var user = createUser.createUser(data);
        return user;
    }

    @Override
    public UserReturnDTO getUserByTokenJWT(String id) {
        var user = getUserByTokenJWT.getUserByID(id);
        return user;
    }

    @Override
    public String forgotPassword(UserOnlyLoginDTO data) {
        forgotPassword.forgotPassword(data);
        return "Email sent with token for password reset";
    }

    @Override
    public String resetPassword(UserResetPassDTO data) {
        resetPassword.resetPassword(data);
        return "Password successfully updated!";
    }

    @Override
    public UserIdDTO getUserIdByJWT(String token) {
        var userId = getUserIdByJWT.getUserByJWT(token);
        return userId;
    }

    @Override
    public UserReturnDTO updateUserInfo(UserGetInfoUpdateDTO data, String tokenJWT) {
        var updatedUser = updateUser.updateUserInfo(data, tokenJWT);
        return updatedUser;
    }

    @Override
    public void deleteUser(String tokenJWT) {
        deleteUser.deleteUser(tokenJWT);
    }

    @Override
    public UserPublicReturnDTO getPublicInfoByUserId(String userId) {
        return getPublicInfo.getPublicInfoByUserId(userId);
    }
}
