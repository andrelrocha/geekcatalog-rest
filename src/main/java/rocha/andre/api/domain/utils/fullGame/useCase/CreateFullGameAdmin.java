package rocha.andre.api.domain.utils.fullGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.domain.utils.fullGame.DTO.FullGameReturnDTO;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameConsolesProcessor;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameCreateEntityProcessor;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameGenresProcessor;
import rocha.andre.api.domain.utils.fullGame.utils.FullGameStudiosProcessor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CreateFullGameAdmin {
    @Autowired
    private FullGameCreateEntityProcessor fullGameCreateEntityProcessor;
    @Autowired
    private FullGameConsolesProcessor fullGameConsolesProcessor;
    @Autowired
    private FullGameGenresProcessor fullGameGenresProcessor;
    @Autowired
    private FullGameStudiosProcessor fullGameStudiosProcessor;

    private static final Logger logger = LoggerFactory.getLogger(CreateFullGameAdmin.class);

    @Transactional(rollbackFor = Exception.class)
    public FullGameReturnDTO createGameFromIGDBInfo(CreateFullGameDTO data) {
        var newGame = fullGameCreateEntityProcessor.processCreateGameEntity(data);
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
    }
}