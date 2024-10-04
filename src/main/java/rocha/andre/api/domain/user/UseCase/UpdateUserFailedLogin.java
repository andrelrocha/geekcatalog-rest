package rocha.andre.api.domain.user.UseCase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.auditLog.LoginStatus;
import rocha.andre.api.domain.auditLog.useCase.RegisterAuditLog;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UpdateUserFailedLogin {

    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterAuditLog registerAuditLog;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFailedLogin(String login) {
        User user = userRepository.findByLoginToHandle(login);

        if (user == null) {
            throw new ValidationException("Usuário não encontrado para o login: " + login);
        }

        int failedAttempts = user.getAccessFailedCount() + 1;

        if (failedAttempts > MAX_ATTEMPTS) {
            user.setLockoutEnabled(true);
            user.setLockoutEnd(LocalDateTime.now().plusMinutes(15));
        } else {
            user.setAccessFailedCount(failedAttempts);
        }

        userRepository.save(user);
    }
}
