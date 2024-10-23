package rocha.andre.api.domain.utils.views.OAuth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RenderLoginOAuth {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public ModelAndView showLoginOptions() {
        ModelAndView modelAndView = new ModelAndView("oauth-options");

        modelAndView.addObject("client_id", clientId);
        modelAndView.addObject("redirect_uri", redirectUri);

        return modelAndView;
    }
}
