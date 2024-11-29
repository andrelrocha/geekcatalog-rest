package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioReturnDTO;
import rocha.andre.api.domain.gameStudio.DTO.UpdateGameStudioDTO;

public interface GameStudioService {
    Page<GameStudioReturnDTO> getAllGameStudiosByGameId(String gameId, Pageable pageable);
    GameStudioReturnDTO createGameStudio(GameStudioDTO data);
    Page<GameStudioReturnDTO> updateGameStudios(UpdateGameStudioDTO data, String gameId);
}
