package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;

@Component
public class GetAllGamesPageable {
    @Autowired
    private GameRepository repository;

    public Page<GameReturnDTO> getGamesPageable(Pageable pageable) {
        return repository.findAllGames(pageable).map(GameReturnDTO::new);
    }
}
