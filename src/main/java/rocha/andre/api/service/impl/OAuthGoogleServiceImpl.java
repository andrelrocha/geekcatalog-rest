package rocha.andre.api.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.user.UseCase.AuthenticateGoogleUser;
import rocha.andre.api.domain.user.UseCase.RenderLoginOAuth;
import rocha.andre.api.infra.security.AuthTokensDTO;
import rocha.andre.api.infra.utils.OAuth.ExchangeCodeForAccessToken;
import rocha.andre.api.service.OAuthGoogleService;

@Service
public class OAuthGoogleServiceImpl implements OAuthGoogleService {
    @Autowired
    private AuthenticateGoogleUser authenticateGoogleUser;
    @Autowired
    private ExchangeCodeForAccessToken exchangeCodeForAccessToken;
    @Autowired
    private RenderLoginOAuth renderLoginOAuth;

    public String exchangeCodeForAccessToken(String code) {
        return exchangeCodeForAccessToken.exchangeCodeForAccessToken(code);
    }

    @Override
    public AuthTokensDTO signInGoogleUser(String googleAccessToken, HttpServletRequest request) {
        return authenticateGoogleUser.signInGoogleUser(googleAccessToken, request);
    }

    @Override
    public ModelAndView showLoginOptions() {
        return renderLoginOAuth.showLoginOptions();
    }
}
