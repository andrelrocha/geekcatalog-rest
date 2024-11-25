package rocha.andre.api.domain.utils.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.service.UserService;

@Component
public class SignInView {
    @Autowired
    private UserService userService;

    public ModelAndView signInForm() {
        ModelAndView modelAndView = new ModelAndView("sign-in");

        return modelAndView;
    }

}
