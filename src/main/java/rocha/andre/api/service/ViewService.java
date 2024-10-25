package rocha.andre.api.service;

import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryRequestDTO;

public interface ViewService {
    ModelAndView showLoginOptions();
    ModelAndView createGameFromIGDB(IGDBQueryRequestDTO data);
    ModelAndView selectGame();
}
