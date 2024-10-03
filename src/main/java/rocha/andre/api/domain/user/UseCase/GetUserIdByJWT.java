package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.user.DTO.UserIdDTO;
import rocha.andre.api.infra.security.TokenService;

@Component
public class GetUserIdByJWT {
    @Autowired
    private TokenService tokenService;

    public UserIdDTO getUserByJWT(String token) {
        var userId = tokenService.getIdClaim(token);
        userId = userId.replace("\"", "").replace("\\", "");

        return new UserIdDTO(userId);
    }

}
