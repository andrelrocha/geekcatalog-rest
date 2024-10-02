package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.user.DTO.UserLoginDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.infra.security.AuthTokensDTO;
import rocha.andre.api.infra.security.TokenService;

@Component
public class PerformLogin {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    TokenService tokenService;

    public AuthTokensDTO performLogin(UserLoginDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        //está chamando authenticateService
        Authentication authentication = manager.authenticate(authenticationToken);

        User userAuthenticated = (User) authentication.getPrincipal();

        String accessToken = tokenService.generateJwtToken(userAuthenticated);
        String refreshToken = tokenService.generateRefreshToken(userAuthenticated);

        return new AuthTokensDTO(accessToken, refreshToken);
    }
}
