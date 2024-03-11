package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.GameRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReturnAllGamesUseCase {
    @Autowired
    private GameRepository repository;

    public List<GameDTO> returnAllGames() {
        var games = repository.findAll();

        List<GameDTO> gameDTOs = new ArrayList<>();

        for (Game game : games) {
            var gameDTO = new GameDTO(game.getName(), game.getLength(), game.getMetacritic(), game.getExcitement(), game.isPlayed(), game.getGenre());

            gameDTOs.add(gameDTO);
        }

        return gameDTOs;
    }

}
