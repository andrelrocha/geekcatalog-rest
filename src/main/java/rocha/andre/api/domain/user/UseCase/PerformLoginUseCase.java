package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.infra.security.TokenService;

@Component
public class PerformLoginUseCase {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    TokenService tokenService;

    public TokenJwtDto performLogin(UserDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        //está chamando authenticateService
        Authentication authentication = manager.authenticate(authenticationToken);
        User userAuthenticated = (User) authentication.getPrincipal();


        String tokenJwt = tokenService.generateJwtToken(userAuthenticated);

        return new TokenJwtDto(tokenJwt);
    }
}
