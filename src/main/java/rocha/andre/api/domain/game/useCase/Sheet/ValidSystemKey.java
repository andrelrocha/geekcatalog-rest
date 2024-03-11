package rocha.andre.api.domain.game.useCase.Sheet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.SystemSecretDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

import javax.security.sasl.AuthenticationException;

@Component
public class ValidSystemKey {
    @Value("${system.secret}")
    private String systemSecret;

    public boolean isSystemKeyValid(SystemSecretDTO dto) throws Exception {
        var secret = dto.secret().toLowerCase();

        var isValid = secret.equals(systemSecret);

        if (!isValid) {
            throw new BadCredentialsException("Você não tem autorização para a operação desejada, contate o administrador do projeto.");
        }

        return isValid;
    }
}
