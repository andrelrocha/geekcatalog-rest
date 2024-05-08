package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioReturnDTO;
import rocha.andre.api.domain.gameStudio.useCase.CreateGameStudio;
import rocha.andre.api.domain.gameStudio.useCase.GetAllGameStudioByGameID;
import rocha.andre.api.service.GameStudioService;

@Service
public class GameStudioServiceImpl implements GameStudioService {
    @Autowired
    private GetAllGameStudioByGameID getAllGameStudioByGameID;
    @Autowired
    private CreateGameStudio createGameStudio;


    @Override
    public Page<GameStudioReturnDTO> getAllGameStudiosByGameId(String gameId, Pageable pageable) {
        var gameStudios = getAllGameStudioByGameID.getAllGameStudiosByGameId(gameId, pageable);
        return gameStudios;
    }

    @Override
    public GameStudioReturnDTO createGameStudios(GameStudioDTO data) {
        var newGameStudio = createGameStudio.createGameStudio(data);
        return newGameStudio;
    }
}
