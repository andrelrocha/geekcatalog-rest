package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.DTO.GameUpdateDTO;
import rocha.andre.api.domain.game.DTO.SystemSecretDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.domain.game.useCase.CRUD.*;
import rocha.andre.api.domain.game.useCase.Sheet.*;
import rocha.andre.api.service.GameService;

import javax.security.sasl.AuthenticationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private CreateGameUseCase createGameUseCase;
    @Autowired
    private ExcelToCSVConverter excelToCSVConverter;

    @Autowired
    private ConvertGamesOnDBtoCSV convertGamesOnDBtoCSV;

    @Autowired
    private ConvertCSVtoXLS convertCSVtoXLS;

    @Autowired
    private DoneGameUseCase doneGameUseCase;

    @Autowired
    private GetAllGamesPageable getAllGamesPageable;

    @Autowired
    private GetGameByIDUseCase getGameByIDUseCase;
    @Autowired
    private GetGameByNameContains getGameByNameContains;

    @Autowired
    private GetRandomGame getRandomGame;
    @Autowired
    private UpdateGameUseCase updateGameUseCase;

    @Autowired
    private ValidSystemKey validSystemKey;

    @Autowired
    private SaveGamesOnDB saveGamesOnDB;

    @Autowired
    private ReturnAllGamesUseCase returnAllGamesUseCase;

    @Override
    public String excelToCSV() {
        var string = excelToCSVConverter.convertXlsxToCsv();
        return string;
    }

    @Override
    public List<Game> saveGamesOnDb() throws IOException {
        var gamesOnDb = saveGamesOnDB.saveGamesOnDataBase();
        return gamesOnDb;
    }

    @Override
    public File gamesToXLS(SystemSecretDTO dto) throws Exception {
        //authenticating system's admin
        validSystemKey.isSystemKeyValid(dto);

        var csvFile = convertGamesOnDBtoCSV.convertGamesToCSV();
        var xlsFile = convertCSVtoXLS.convertCSVtoXLS(csvFile);
        return xlsFile;
    }

    @Override
    public GameReturnDTO createGame(GameDTO data) {
        var game = createGameUseCase.createGame(data);
        return game;
    }

    @Override
    public void doneGame(Long id) {
        doneGameUseCase.doneGame(id);
    }

    @Override
    public List<GameDTO> getAllGames() {
        var games = returnAllGamesUseCase.returnAllGames();
        return games;
    }

    @Override
    public GameReturnDTO getGameById(Long id) {
        var game = getGameByIDUseCase.getGameById(id);
        return game;
    }

    @Override
    public Page<GameReturnDTO> getGameByNameContains(String nameCompare, Pageable pageable) {
        var games = getGameByNameContains.getGameByNameContains(nameCompare, pageable);
        return games;
    }

    @Override
    public Page<GameReturnDTO> getGamesPageable(Pageable pageable) {
        var gamesPageable = getAllGamesPageable.getGamesPageable(pageable);
        return gamesPageable;
    }

    @Override
    public GameReturnDTO updateGame(GameDTO dto, String gameId) {
        var game = updateGameUseCase.updateGame(dto, gameId);
        return game;
    }

    @Override
    public GameReturnDTO suggestionGame() {
        var game = getRandomGame.suggestionGame();
        return game;
    }
}
