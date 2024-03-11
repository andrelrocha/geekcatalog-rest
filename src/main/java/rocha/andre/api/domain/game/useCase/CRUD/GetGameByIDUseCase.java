package rocha.andre.api.domain.game.useCase.CRUD;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.GameRepository;

@Component
public class GetGameByIDUseCase {
    @Autowired
    private GameRepository repository;

    public GameReturnDTO getGameById(Long id) {
        var game = repository.findById(id)
                .orElseThrow( () -> new RuntimeException("Não foi encontrado o jogo com o ID informado."));

        return new GameReturnDTO(game);
    }
}
