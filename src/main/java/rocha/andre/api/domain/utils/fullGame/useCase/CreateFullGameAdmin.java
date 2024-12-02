package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rocha.andre.api.service.FullGameService;

@Component
public class CreateFullGameAdmin {
    @Autowired
    private FullGameService fullGameService;

    private static final Logger logger = LoggerFactory.getLogger(CreateFullGameAdmin.class);

    @Transactional(rollbackFor = Exception.class)
    public FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data) {
        var newGame = fullGameService.manageCreateGameEntity(data);
        logger.info("Jogo criado com ID: {}", newGame.id());

        logger.info("Processando gêneros...");
        var newGameGenres = fullGameService.manageFullGameGenres(data.genres(), String.valueOf(newGame.id()));

        logger.info("Processando consoles...");
        var newGameConsoles = fullGameService.manageFullGameConsoles(data.consoles(), String.valueOf(newGame.id()));

        logger.info("Processando estúdios...");
        var newGameStudios = fullGameService.manageFullGameStudios(data.studios(), String.valueOf(newGame.id()));

        logger.info("Criação do jogo a partir dos dados obtidos do IGDB concluída com sucesso!");
        var methodReturn = new FullGameReturnDTO(newGame, newGameConsoles, newGameGenres, newGameStudios);
        logger.info(methodReturn.toString());

        return methodReturn;
    }
}