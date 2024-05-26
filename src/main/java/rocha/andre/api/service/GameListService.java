package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;

public interface GameListService {
    Page<GameListReturnDTO> getGamesByListID(Pageable pageable, String listId);
    GameListFullReturnDTO getGameListByID(String gameListID);
}
