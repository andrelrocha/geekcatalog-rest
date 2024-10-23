package rocha.andre.api.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.infra.security.AuthTokensDTO;

public interface OAuthGoogleService {
    String exchangeCodeForAccessToken(String code);
    AuthTokensDTO signInGoogleUser(String googleAccessToken, HttpServletRequest request);
}
