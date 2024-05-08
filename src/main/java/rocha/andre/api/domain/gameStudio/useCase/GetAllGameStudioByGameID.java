package rocha.andre.api.domain.gameStudio.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioReturnDTO;
import rocha.andre.api.domain.gameStudio.GameStudioRepository;

import java.util.UUID;

@Component
public class GetAllGameStudioByGameID {
    @Autowired
    private GameStudioRepository repository;

    public Page<GameStudioReturnDTO> getAllGameStudiosByGameId(String gameId, Pageable pageable) {
        var gameIdUUID = UUID.fromString(gameId);

        var gameStudiosByGame = repository.findAllGameStudiosByGameId(gameIdUUID, pageable).map(GameStudioReturnDTO::new);

        return gameStudiosByGame;
    }
}