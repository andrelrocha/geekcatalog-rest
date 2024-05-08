package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.useCase.CreateGameGenre;
import rocha.andre.api.domain.gameGenre.useCase.GetAllGameGenreByGameID;
import rocha.andre.api.service.GameGenreService;

@Service
public class GameGenreServiceImpl implements GameGenreService {
    @Autowired
    private GetAllGameGenreByGameID getAllGameGenreByGameID;
    @Autowired
    private CreateGameGenre createGameGenre;

    @Override
    public GameGenreReturnDTO createGameGenre(GameGenreDTO data) {
        var newGameGenre = createGameGenre.createGameGenre(data);
        return newGameGenre;
    }

    @Override
    public Page<GameGenreReturnDTO> getAllGameGenresByGameId(String gameId, Pageable pageable) {
        var gameGenresByGameId = getAllGameGenreByGameID.getAllGameGenresByGameId(gameId, pageable);
        return gameGenresByGameId;
    }
}
