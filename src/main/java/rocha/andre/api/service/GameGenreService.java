package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;

public interface GameGenreService {
    GameGenreReturnDTO createGameGenre(GameGenreDTO data);
    Page<GameGenreReturnDTO> getAllGameGenresByGameId(String gameId, Pageable pageable);
}
