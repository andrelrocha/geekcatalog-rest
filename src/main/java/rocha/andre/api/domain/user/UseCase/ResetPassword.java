package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.domain.user.DTO.UserResetPassDTO;

import java.time.LocalDateTime;

@Component
public class ResetPassword {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void resetPassword(UserResetPassDTO data) {
        try {
            var userExists = repository.existsByLogin(data.login());

            if (!userExists) {
                throw new ValidationException("No user was found for the provided login");
            }

            var user = repository.findByLoginToHandle(data.login());
            var tokenMail = user.getTokenMail();
            var tokenExpiration = user.getTokenExpiration();
            var now = LocalDateTime.now();

            var tokenIsValid = tokenMail.equals(data.tokenMail()) && now.isBefore(tokenExpiration);

            if (tokenIsValid) {
                String encodedPassword = bCryptPasswordEncoder.encode(data.password());
                user.setPassword(encodedPassword);
            } else {
                throw new ValidationException("Invalid reset accessToken key");
            }
        }
        catch (Exception e) {
            throw new ValidationException("Something has happened during the reset password process: " + e.getMessage());
        }
    }
}