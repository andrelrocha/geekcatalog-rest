package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.utils.views.OAuth.RenderLoginOAuth;
import rocha.andre.api.service.ViewService;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private RenderLoginOAuth renderLoginOAuth;

    @Override
    public ModelAndView showLoginOptions() {
        return renderLoginOAuth.showLoginOptions();
    }
}
