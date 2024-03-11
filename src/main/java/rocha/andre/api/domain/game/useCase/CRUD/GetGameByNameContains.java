package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;


@Component
public class GetGameByNameContains {
    @Autowired
    private GameRepository repository;

    public Page<GameReturnDTO> getGameByNameContains(String nameCompare, Pageable pageable) {
        var games = repository.findGamesByNameContaining(nameCompare, pageable).map(GameReturnDTO::new);
        if (games.isEmpty()) {
            return Page.empty();
        }

        return games;
    }
}