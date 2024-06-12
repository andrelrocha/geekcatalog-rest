package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.game.DTO.GameAndIdDTO;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.useCase.*;
import rocha.andre.api.service.GameService;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private CreateGame createGame;
    @Autowired
    private DeleteGame deleteGame;
    @Autowired
    private GetAllGamesPageable getAllGamesPageable;
    @Autowired
    private GetAllGamesWithID getAllGamesWithID;
    @Autowired
    private SearchByName searchByName;
    @Autowired
    private UpdateGame updateGame;

    @Override
    public Page<GameReturnDTO> getAllGames(Pageable pageable) {
        var games = getAllGamesPageable.getAllGames(pageable);
        return games;
    }

    @Override
    public Page<GameReturnDTO> getAllGamesByName(Pageable pageable, String nameCompare) {
        var gamesComparable = searchByName.getAllGamesByName(pageable, nameCompare);
        return gamesComparable;
    }

    @Override
    public GameReturnDTO createGame(GameDTO data) {
        var newGame = createGame.createGame(data);
        return newGame;
    }

    @Override
    public GameReturnDTO updateGame(GameDTO data, String gameId) {
        var updatedGame = updateGame.updateGame(data, gameId);
        return updatedGame;
    }

    @Override
    public Page<GameAndIdDTO> getAllGamesWithID(Pageable pageable) {
        var gamesWithId = getAllGamesWithID.getAllGamesWithID(pageable);
        return gamesWithId;
    }

    @Override
    public void deleteGame(String gameId) {
        deleteGame.deleteGame(gameId);
    }
}
