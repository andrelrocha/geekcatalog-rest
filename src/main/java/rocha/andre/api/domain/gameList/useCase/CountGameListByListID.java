package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.CountGameListReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.GameListRepository;

import java.util.UUID;

@Component
public class CountGameListByListID {
    @Autowired
    private GameListRepository gameListRepository;

    public CountGameListReturnDTO countGamesByListID(String listId) {
        var listIdUUID = UUID.fromString(listId);

        var gameListCount = gameListRepository.countGameListsByListId(listIdUUID);

        return new CountGameListReturnDTO(gameListCount);
    }
}
