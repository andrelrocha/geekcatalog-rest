package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.GameListRepository;

import java.util.UUID;

@Component
public class GetGameListByListID {
    @Autowired
    private GameListRepository gameListRepository;

    public Page<GameListReturnDTO> getGamesByListID(Pageable pageable, String listId) {
        var listIdUUID = UUID.fromString(listId);

        var gameListPageable = gameListRepository.pageableGameListByListId(listIdUUID, pageable).map(GameListReturnDTO::new);

        return gameListPageable;
    }
}
