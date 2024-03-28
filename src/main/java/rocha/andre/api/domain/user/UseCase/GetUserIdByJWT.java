package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.infra.security.TokenService;

@Component
public class GetUserIdByJWT {
    @Autowired
    private TokenService tokenService;

    public String getUserByJWT(String token) {
        var userId = tokenService.getClaim(token);

        return userId;
    }

}
