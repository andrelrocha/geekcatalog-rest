package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.auditLog.useCase.RegisterAuditLog;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.time.ZoneId;
import java.time.LocalDateTime;

@Component
public class UpdateUserFailedLogin {

    private static final int MAX_ATTEMPTS = 5;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterAuditLog registerAuditLog;
    @Autowired
    private TaskScheduler taskScheduler;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFailedLogin(String login) {
        User user = userRepository.findByLoginToHandle(login);

        if (user == null) {
            throw new ValidationException("No user was found for the provided login: " + login);
        }

        int failedAttempts = user.getAccessFailedCount() + 1;

        if (failedAttempts >= MAX_ATTEMPTS) {
            var lockoutEndTime = LocalDateTime.now().plusMinutes(15);
            user.setLockoutEnabled(true);
            user.setLockoutEnd(lockoutEndTime);
            taskScheduler.schedule(() -> unlockUserAccount(user),
                    lockoutEndTime.atZone(ZoneId.systemDefault()).toInstant());
        } else {
            user.setAccessFailedCount(failedAttempts);
        }

        userRepository.save(user);
    }

    private void unlockUserAccount(User user) {
        user.setLockoutEnabled(false);
        user.setAccessFailedCount(0);
        user.setLockoutEnd(null);
        userRepository.save(user);
    }
}
