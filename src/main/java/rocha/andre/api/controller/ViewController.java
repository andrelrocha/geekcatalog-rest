package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.service.ViewService;

@RestController
@RequestMapping("/view")
@Tag(name = "Views Routes Mapped on Controller, generated using Thymeleaf")
public class ViewController {
    @Autowired
    private ViewService viewService;
    @GetMapping("/oauth")
    public ModelAndView showLoginOptions() {
        return viewService.showLoginOptions();
    }


}
