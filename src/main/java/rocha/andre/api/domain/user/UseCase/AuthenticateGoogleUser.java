package rocha.andre.api.domain.user.UseCase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.auditLog.LoginStatus;
import rocha.andre.api.domain.auditLog.useCase.RegisterAuditLog;
import rocha.andre.api.domain.authenticationType.AuthenticationTypeRepository;
import rocha.andre.api.domain.user.DTO.UserCreateDTO;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.domain.user.UserTheme;
import rocha.andre.api.domain.userAuthenticationType.DTO.CreateUserAuthenticationTypeDTO;
import rocha.andre.api.domain.userAuthenticationType.UserAuthenticationType;
import rocha.andre.api.domain.userAuthenticationType.UserAuthenticationTypeRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.security.AuthTokensDTO;
import rocha.andre.api.infra.security.TokenService;
import rocha.andre.api.infra.utils.OAuth.GetGoogleUserInfo;
import rocha.andre.api.infra.utils.OAuth.GoogleUserInfo;

import java.security.SecureRandom;
import java.util.UUID;

@Component
public class AuthenticateGoogleUser {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 20;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationTypeRepository authenticationTypeRepository;
    @Autowired
    private UserAuthenticationTypeRepository userAuthenticationTypeRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private GetGoogleUserInfo getGoogleUserInfo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RegisterAuditLog registerAuditLog;

    public AuthTokensDTO signInGoogleUser(String googleAccessToken, HttpServletRequest request) {
        GoogleUserInfo googleUser = getGoogleUserInfo.getGoogleUserInfo(googleAccessToken);

        var user = userAuthenticationTypeRepository.findUserByOAuthId(googleUser.sub());

        if (user == null) {
            boolean userExists = userRepository.userExistsByLogin(googleUser.email());

            if (userExists) {
                throw new ValidationException("Email on user creation by google sign in already exists in our database");
            }

            var newUsername = generateUniqueUsername(googleUser.email());

            var userCreateDTO = new UserCreateDTO(
                    new UserDTO(
                            googleUser.email(),
                            "N/A",
                            googleUser.name(),
                            null, // CPF não disponível
                            null, // Telefone não disponível
                            null, // Data de aniversário não disponível
                            null, // countryId
                            newUsername,
                            false, // twoFactorEnabled
                            false, // refreshTokenEnabled
                            UserTheme.LIGHT.toString()
                    ),
                    null // country, se aplicável
            );

            user = new User(userCreateDTO);

            String encodedPassword = bCryptPasswordEncoder.encode(generateUniquePassword());
            user.setPassword(encodedPassword);

            var userOnDB = userRepository.save(user);

            var authenticationTypeGoogle = authenticationTypeRepository.findByName("google");

            var userAuthTypeDTO = new CreateUserAuthenticationTypeDTO(authenticationTypeGoogle, userOnDB, googleUser.sub());

            var userAuthType = new UserAuthenticationType(userAuthTypeDTO);

            userAuthenticationTypeRepository.save(userAuthType);
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = null;
        if (user.isRefreshTokenEnabled()) {
            refreshToken = tokenService.generateRefreshToken(user);
        }

        registerAuditLog.logLogin(
                user.getLogin(),
                request,
                LoginStatus.SUCCESS,
                request.getHeader("User-Agent"),
                //codigo do login social pelo google no banco
                UUID.fromString("1c8b6843-b182-49c8-9083-3bf35cc50870")
        );

        return new AuthTokensDTO(accessToken, refreshToken);
    }

    public String generateUniqueUsername(String email) {
        var username = email.trim().substring(0, email.indexOf("@"));
        int counter = 0;

        while (userRepository.userExistsByUsername(username)) {
            counter++;
            username = username + String.format("%03d", counter);
        }

        return username;
    }

    public static String generateUniquePassword() {
        var random = new SecureRandom();
        var password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
