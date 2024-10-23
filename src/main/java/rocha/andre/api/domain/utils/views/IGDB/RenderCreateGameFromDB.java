package rocha.andre.api.domain.utils.views.IGDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryRequestDTO;
import rocha.andre.api.domain.utils.API.Twitch.TwitchAuth;
import rocha.andre.api.service.IGDBService;

@Component
public class RenderCreateGameFromDB {
    @Autowired
    private TwitchAuth twitchAuth;
    @Autowired
    private IGDBService igdbService;

    public ModelAndView createGameFromIGDB(IGDBQueryRequestDTO data) {
        var queryInfo = twitchAuth.authenticateUser(data.gameName());
        var igdbResponse = igdbService.fetchGameDetails(queryInfo);

        var modelAndView = new ModelAndView("create-games");

        modelAndView.addObject("gameName", igdbResponse.gameName());
        modelAndView.addObject("genresName", igdbResponse.genresName());
        modelAndView.addObject("platformsName", igdbResponse.platformsName());
        modelAndView.addObject("involvedCompanies", igdbResponse.involvedCompanies());

        return modelAndView;
    }
}
