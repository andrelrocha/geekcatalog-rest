package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameList.DTO.*;
import rocha.andre.api.domain.genres.DTO.GenreCountDTO;

import java.util.ArrayList;

public interface GameListService {
    Page<GameListUriReturnDTO> getGamesByListID(Pageable pageable, String listId);
    GameListFullReturnDTO getGameListByID(String gameListID);
    Page<GameListReturnDTO> getLatestGamesByListID(Pageable pageable, String listId);
    GameListGameAndConsolesDTO getGameInfoByGameListID(String gameListId);
    Page<GenreCountDTO> getAllGameListGenresByUserId(String tokenJWT, Pageable pageable);
    CountGameListReturnDTO countGamesByListID(String listId);
    ArrayList<GameListBulkReturnDTO> addBulkGamesToList(GameListBulkCreateDTO data);
    GameListFullReturnDTO addGameList(GameListDTO data);
    GameListFullReturnDTO updateGameList(GameListUpdateRequestDTO data, String gameListId);
    void deleteGameList(DeleteGameListDTO data);
}
