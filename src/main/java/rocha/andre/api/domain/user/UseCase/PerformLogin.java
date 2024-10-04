package rocha.andre.api.domain.user.UseCase;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.auditLog.LoginStatus;
import rocha.andre.api.domain.auditLog.useCase.RegisterAuditLog;
import rocha.andre.api.domain.user.DTO.UserLoginDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.infra.security.AuthTokensDTO;
import rocha.andre.api.infra.security.TokenService;

import java.util.UUID;

@Component
public class PerformLogin {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RegisterAuditLog registerAuditLog;

    // Por enquanto só é permitido login por OWNLOGIN "a05fa936-8640-4474-b5cd-963a36ff5fe6"
    public AuthTokensDTO performLogin(UserLoginDTO data, HttpServletRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        try {
            Authentication authentication = manager.authenticate(authenticationToken);

            User userAuthenticated = (User) authentication.getPrincipal();

            String accessToken = tokenService.generateAccessToken(userAuthenticated);
            String refreshToken = tokenService.generateRefreshToken(userAuthenticated);

            registerAuditSuccess(data, request);

            return new AuthTokensDTO(accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            registerAuditFailure(data, request);
            throw new BadCredentialsException("Login ou senha errados.");
        } catch (Exception e) {
            registerAuditFailure(data, request);
            throw new RuntimeException("Ocorreu um erro durante o login: " + e.getMessage());
        }
    }

    public void registerAuditFailure(UserLoginDTO data, HttpServletRequest request) {
        registerAuditLog.logLogin(
                data.login(),
                request,
                LoginStatus.FAILURE,
                request.getHeader("User-Agent"),
                UUID.fromString("a05fa936-8640-4474-b5cd-963a36ff5fe6")
        );
    }

    public void registerAuditSuccess(UserLoginDTO data, HttpServletRequest request) {
        registerAuditLog.logLogin(
                data.login(),
                request,
                LoginStatus.FAILURE,
                request.getHeader("User-Agent"),
                UUID.fromString("a05fa936-8640-4474-b5cd-963a36ff5fe6")
        );
    }
}
