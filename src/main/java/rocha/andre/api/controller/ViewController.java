package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryRequestDTO;
import rocha.andre.api.service.ViewService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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

    @GetMapping("/admin/creategame/{gameName}")
    public ModelAndView createGameWithIGDBInfo(@PathVariable("gameName") String gameName) {
        String decodedGameName = URLDecoder.decode(gameName, StandardCharsets.UTF_8);
        return viewService.createGameFromIGDB(new IGDBQueryRequestDTO(decodedGameName));
    }

    @GetMapping("/admin/selectgame")
    public ModelAndView renderTypeGamenameView() {
        return viewService.selectGame();
    }

}
