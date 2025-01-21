package rocha.andre.api.domain.auditLog.useCase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.auditLog.AuditLog;
import rocha.andre.api.domain.auditLog.AuditLogRepository;
import rocha.andre.api.domain.auditLog.LoginStatus;
import rocha.andre.api.domain.authenticationType.AuthenticationTypeRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RegisterAuditLog {
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private AuthenticationTypeRepository authenticationTypeRepository;

    //Isola a transação para que se torne independente em caso de exceção lançada que geraria o rollback quando fosse chamado em outra parte do código
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logLogin(String userName, HttpServletRequest request, LoginStatus loginStatus, String userAgent, UUID authenticationTypeId) {
        AuditLog auditLog = new AuditLog();

        var authenticationType = authenticationTypeRepository.findById(authenticationTypeId)
                .orElseThrow(() -> new ValidationException("No authentication type found with the provided ID"));

        auditLog.setUserName(userName);
        auditLog.setLoginTime(LocalDateTime.now());
        auditLog.setIpAddress(request.getRemoteAddr());
        auditLog.setAuthenticationType(authenticationType);
        auditLog.setLoginStatus(loginStatus);
        auditLog.setUserAgent(userAgent);
        auditLog.setHostName(request.getRemoteHost());
        auditLog.setServerName(request.getServerName());

        auditLogRepository.save(auditLog);
    }
}
