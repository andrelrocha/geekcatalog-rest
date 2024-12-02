package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.genres.DTO.GenreReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.utils.create.processor.manager.FullGameConsolesManager;
import rocha.andre.api.domain.utils.fullGame.utils.create.processor.manager.FullGameCreateEntityManager;
import rocha.andre.api.domain.utils.fullGame.utils.create.processor.manager.FullGameGenresManager;
import rocha.andre.api.domain.utils.fullGame.utils.create.processor.manager.FullGameStudiosManager;
import rocha.andre.api.service.FullGameService;

import java.util.ArrayList;
import java.util.List;

@Service
public class FullGameServiceImpl implements FullGameService {
    @Autowired
    private FullGameCreateEntityManager gameCreateEntityManager;
    @Autowired
    private FullGameConsolesManager gameConsolesManager;
    @Autowired
    private FullGameGenresManager gameGenresManager;
    @Autowired
    private FullGameStudiosManager gameStudiosManager;

    @Override
    public GameReturnDTO manageCreateGameEntity(CreateFullGameDTO data) {
        return gameCreateEntityManager.manageCreateGameEntity(data);
    }

    @Override
    public ArrayList<ConsoleReturnDTO> manageFullGameConsoles(List<String> consoles, String gameId) {
        return gameConsolesManager.manageFullGameConsoles(consoles, gameId);
    }

    @Override
    public ArrayList<GenreReturnDTO> manageFullGameGenres(List<String> genres, String gameId) {
        return gameGenresManager.manageFullGameGenres(genres, gameId);
    }

    @Override
    public ArrayList<StudioReturnFullGameInfo> manageFullGameStudios(List<CompanyReturnDTO> studios, String gameId) {
        return gameStudiosManager.manageFullGameStudios(studios, gameId);
    }
}
