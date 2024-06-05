package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.gameList.DTO.*;
import rocha.andre.api.domain.gameList.useCase.*;
import rocha.andre.api.service.GameListService;

import java.util.ArrayList;

@Service
public class GameListServiceImpl implements GameListService {
    @Autowired
    private AddBulkGameList addBulkGameList;
    @Autowired
    private AddGameList addGameList;
    @Autowired
    private CountGameListByListID countGameListByListID;
    @Autowired
    private DeleteGameList deleteGameList;
    @Autowired
    private GetGameListByListID getGameListByListID;
    @Autowired
    private GetGameListByID getGameListByID;
    @Autowired
    private GetLatestGameListByListID getLatestGameListByListID;
    @Autowired
    private UpdateGameList updateGameList;

    @Override
    public Page<GameListUriReturnDTO> getGamesByListID(Pageable pageable, String listId) {
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

    @Override
    public ArrayList<GameListBulkReturnDTO> addBulkGamesToList(GameListBulkCreateDTO data) {
        return addBulkGameList.addBulkGamesToList(data);
    }

    @Override
    public GameListFullReturnDTO addGameList(GameListDTO data) {
        return addGameList.addGameList(data);
    }

    @Override
    public GameListFullReturnDTO updateGameList(GameListUpdateRequestDTO data, String gameListId) {
        return updateGameList.updateGameList(data, gameListId);
    }

    @Override
    public void deleteGameList(DeleteGameListDTO data) {
        deleteGameList.deleteGameList(data);
    }
}
