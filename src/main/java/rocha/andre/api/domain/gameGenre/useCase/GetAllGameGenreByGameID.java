package rocha.andre.api.domain.gameGenre.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.GameGenreRepository;

import java.util.UUID;

@Component
public class GetAllGameGenreByGameID {
    @Autowired
    private GameGenreRepository repository;

    public Page<GameGenreReturnDTO> getAllGameGenresByGameId(String gameId, Pageable pageable) {
        var gameIdUUID = UUID.fromString(gameId);

        var gameGenresByGame = repository.findAllGameGenresByGameId(gameIdUUID, pageable).map(GameGenreReturnDTO::new);

        return gameGenresByGame;
    }
}