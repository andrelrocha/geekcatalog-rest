package rocha.andre.api.domain.utils.fullGame.utils.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.domain.utils.API.IGDB.utils.StudioCountryMapperFromIGDB;
import rocha.andre.api.domain.utils.API.IGDB.utils.StudioDTOFormatterFromIGDB;
import rocha.andre.api.domain.utils.fullGame.utils.processor.StudioProcessor;
import rocha.andre.api.service.GameStudioService;
import rocha.andre.api.service.StudioService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Service
public class StudioProcessorImpl implements StudioProcessor {
    @Autowired
    private GameStudioService gameStudioService;
    @Autowired
    private StudioCountryMapperFromIGDB studioCountryMapperFromIGDB;
    @Autowired
    private StudioService studioService;
    @Autowired
    private StudioDTOFormatterFromIGDB studioDTOFormatterFromIGDB;
    private static final Logger logger = LoggerFactory.getLogger(StudioProcessorImpl.class);

    @Override
    public void addGameStudio(List<StudioReturnFullGameInfo> newGameStudios, String gameId, StudioReturnDTO studio) {
        var gameStudioDTO = new GameStudioDTO(gameId, studio.id().toString());
        var gameStudioCreated = gameStudioService.createGameStudio(gameStudioDTO);
        newGameStudios.add(new StudioReturnFullGameInfo(gameStudioCreated.id(), gameStudioCreated.studioName()));
    }

    @Override
    public Map<String, CountryReturnDTO> buildNormalizedStudiosCountryMap(List<CompanyReturnDTO> studios) {
        return studioCountryMapperFromIGDB.buildNormalizedStudiosCountryMap(studios);
    }

    @Override
    public Map<String, StudioReturnDTO> fetchStudiosWithId(List<StudioDTO> studiosDTO) {
        return studioService.getStudiosByName(studiosDTO)
                .stream()
                .collect(Collectors.toMap(
                        studio -> normalizeString(studio.name()),
                        studio -> studio
                ));
    }

    @Override
    public StudioReturnDTO handleStudioCreationOrFetch(CompanyReturnDTO studioData, String gameId, Map<String, CountryReturnDTO> normalizedCountriesWithId, Map<String, StudioReturnDTO> normalizedStudiosWithId) {
        StudioReturnDTO studio = normalizedStudiosWithId.get(studioData.companyName());
        if (studio != null) {
            logger.info("Estúdio '{}' já existe. Associando ao jogo ID: {}", studio.name(), gameId);
            return studio;
        }

        logger.info("Criando novo estúdio '{}'", capitalizeEachWord(studioData.companyName()));

        var studioCountry = normalizeString(studioData.countryInfo().name().common());
        var country = normalizedCountriesWithId.get(studioCountry);

        var newStudio = new StudioDTO(capitalizeEachWord(studioData.companyName()), country.id());

        return studioService.createStudio(newStudio);
    }

    @Override
    public List<StudioDTO> mapStudiosToDTO(List<CompanyReturnDTO> studios, Map<String, CountryReturnDTO> normalizedCountriesWithId) {
        return studioDTOFormatterFromIGDB.mapStudiosToDTO(studios, normalizedCountriesWithId);
    }
}
