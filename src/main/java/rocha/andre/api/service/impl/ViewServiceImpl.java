package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryRequestDTO;
import rocha.andre.api.domain.utils.views.IGDB.RenderCreateGameFromDB;
import rocha.andre.api.domain.utils.views.OAuth.RenderLoginOAuth;
import rocha.andre.api.service.ViewService;

@Service
public class ViewServiceImpl implements ViewService {
    @Autowired
    private RenderCreateGameFromDB renderCreateGameFromDB;
    @Autowired
    private RenderLoginOAuth renderLoginOAuth;

    @Override
    public ModelAndView showLoginOptions() {
        return renderLoginOAuth.showLoginOptions();
    }

    @Override
    public ModelAndView createGameFromIGDB(IGDBQueryRequestDTO data) {
        return renderCreateGameFromDB.createGameFromIGDB(data);
    }
}
