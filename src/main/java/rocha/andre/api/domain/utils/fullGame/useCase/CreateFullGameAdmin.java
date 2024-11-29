package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameConsolesProcessor;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameGenresProcessor;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameStudiosProcessor;
import rocha.andre.api.infra.exceptions.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocha.andre.api.infra.utils.stringFormatter.StringFormatter;
import rocha.andre.api.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class CreateFullGameAdmin {
    @Autowired
    private GameService gameService;
    @Autowired
    private FullGameConsolesProcessor fullGameConsolesProcessor;
    @Autowired
    private FullGameGenresProcessor fullGameGenresProcessor;
    @Autowired
    private FullGameStudiosProcessor fullGameStudiosProcessor;

    private static final Logger logger = LoggerFactory.getLogger(CreateFullGameAdmin.class);

    @Transactional(rollbackFor = Exception.class)
    public FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data) {
        try {
            var gameDTO = new GameDTO(capitalizeEachWord(normalizeString(data.name())), data.metacritic(), data.yearOfRelease());
            var newGame = gameService.createGame(gameDTO);
            logger.info("Jogo criado com ID: {}", newGame.id());

            logger.info("Processando gêneros...");
            var newGameGenres = fullGameGenresProcessor.processFullGameGenres(data.genres(), String.valueOf(newGame.id()));

            logger.info("Processando consoles...");
            var newGameConsoles = fullGameConsolesProcessor.processFullGameConsoles(data.consoles(), String.valueOf(newGame.id()));

            logger.info("Processando estúdios...");
            var newGameStudios = fullGameStudiosProcessor.processFullGameStudios(data.studios(), String.valueOf(newGame.id()));

            logger.info("Criação do jogo a partir dos dados obtidos do IGDB concluída com sucesso!");
            var methodReturn = new FullGameReturnDTO(newGame, newGameConsoles, newGameGenres, newGameStudios);
            logger.info(methodReturn.toString());
            return methodReturn;
        } catch (ValidationException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Erro ao criar o jogo: {}", e.getMessage(), e);
            throw new RuntimeException("Erro interno ao criar o jogo.", e);
        }
    }
}