package rocha.andre.api.domain.gameList.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListUriReturnDTO;
import rocha.andre.api.domain.gameList.GameListRepository;
import rocha.andre.api.domain.imageGame.useCase.GetImageGameByGameID;

import java.util.UUID;

@Component
public class GetGameListByListID {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private GetImageGameByGameID getImageGameByGameID;

    public Page<GameListUriReturnDTO> getGamesByListID(Pageable pageable, String listId) {
        var listIdUUID = UUID.fromString(listId);

        var gameListPageable = gameListRepository.pageableGameListByListId(listIdUUID, pageable).map(gameList -> {
            var gameId = (gameList.getGame().getId()).toString();
            var imageGame = getImageGameByGameID.getImageGamesByGameID(gameId);

            return new GameListUriReturnDTO(gameList, imageGame.imageUrl());
        });

        return gameListPageable;
    }
}
