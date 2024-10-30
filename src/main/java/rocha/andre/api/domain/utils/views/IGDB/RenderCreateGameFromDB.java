package rocha.andre.api.domain.utils.views.IGDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryRequestDTO;
import rocha.andre.api.domain.utils.API.Twitch.TwitchAuth;
import rocha.andre.api.service.IGDBService;

import java.util.ArrayList;

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

        modelAndView.addObject("gameName", igdbResponse != null ? igdbResponse.gameName() : data.gameName());
        modelAndView.addObject("yearOfRelease", igdbResponse != null ? igdbResponse.yearOfRelease() : "");
        modelAndView.addObject("gameCoverUrl", igdbResponse != null ? igdbResponse.cover() : "");
        modelAndView.addObject("genresName", igdbResponse != null ? igdbResponse.genresName() : new ArrayList<>());
        modelAndView.addObject("platformsName", igdbResponse != null ? igdbResponse.platformsName() : new ArrayList<>());
        modelAndView.addObject("involvedCompanies", igdbResponse != null ? igdbResponse.involvedCompanies() : new ArrayList<>());

        return modelAndView;
    }
}
