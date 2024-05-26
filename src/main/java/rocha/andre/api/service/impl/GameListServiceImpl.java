package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameList.DTO.CountGameListReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListFullReturnDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.domain.gameList.useCase.CountGameListByListID;
import rocha.andre.api.domain.gameList.useCase.GetGameListByID;
import rocha.andre.api.domain.gameList.useCase.GetGameListByListID;
import rocha.andre.api.domain.gameList.useCase.GetLatestGameListByListID;
import rocha.andre.api.service.GameListService;

@Service
public class GameListServiceImpl implements GameListService {
    @Autowired
    private CountGameListByListID countGameListByListID;
    @Autowired
    private GetGameListByListID getGameListByListID;
    @Autowired
    private GetGameListByID getGameListByID;
    @Autowired
    private GetLatestGameListByListID getLatestGameListByListID;

    @Override
    public Page<GameListReturnDTO> getGamesByListID(Pageable pageable, String listId) {
        return getGameListByListID.getGamesByListID(pageable, listId);
    }

    @Override
    public GameListFullReturnDTO getGameListByID(String gameListID) {
        return getGameListByID.getGameListByID(gameListID);
    }

    @Override
    public Page<GameListReturnDTO> getLatestGamesByListID(Pageable pageable, String listId) {
        return getLatestGameListByListID.getLatestGamesByListID(pageable, listId);
    }

    @Override
    public CountGameListReturnDTO countGamesByListID(String listId) {
        return countGameListByListID.countGamesByListID(listId);
    }
}
