package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;

public interface GameService {
    Page<GameReturnDTO> getAllGames(Pageable pageable);
    GameReturnDTO createGame(GameDTO data);
    GameReturnDTO updateGame(GameDTO data, String gameId);
}
