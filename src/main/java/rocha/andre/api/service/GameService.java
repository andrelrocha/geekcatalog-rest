package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.DTO.GameUpdateDTO;
import rocha.andre.api.domain.game.DTO.SystemSecretDTO;
import rocha.andre.api.domain.game.Game;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GameService {
    //CRUD
    GameReturnDTO createGame(GameDTO data);
    void doneGame(Long id);
    List<GameDTO> getAllGames();
    GameReturnDTO getGameById(Long id);
    Page<GameReturnDTO> getGameByNameContains(String nameCompare, Pageable pageable);
    Page<GameReturnDTO> getGamesPageable(Pageable pageable);
    GameReturnDTO updateGame(GameDTO dto, String gameId);
    GameReturnDTO suggestionGame();

    //SHEET API
    String excelToCSV();
    File gamesToXLS(SystemSecretDTO dto) throws Exception;
    List<Game> saveGamesOnDb() throws IOException;
}
