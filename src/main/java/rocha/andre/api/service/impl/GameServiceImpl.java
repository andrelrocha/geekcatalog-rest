package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.useCase.GetAllGamesPageable;
import rocha.andre.api.service.GameService;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GetAllGamesPageable getAllGamesPageable;

    @Override
    public Page<GameReturnDTO> getAllGames(Pageable pageable) {
        var games = getAllGamesPageable.getAllGames(pageable);
        return games;
    }
}
