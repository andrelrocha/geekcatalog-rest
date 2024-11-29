package rocha.andre.api.domain.utils.fullGame.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioReturnFullGameInfo;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.service.CountryService;
import rocha.andre.api.service.GameStudioService;
import rocha.andre.api.service.StudioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.capitalizeEachWord;
import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class FullGameStudiosProcessor {
    @Autowired
    private CountryService countryService;
    @Autowired
    private GameStudioService gameStudioService;
    @Autowired
    private StudioService studioService;
    private static final Logger logger = LoggerFactory.getLogger(FullGameStudiosProcessor.class);

    public ArrayList<StudioReturnFullGameInfo> processFullGameStudios(List<CompanyReturnDTO> studios, String gameId) {
        var normalizedCountriesWithId = processCountryInfo(studios);
        var studiosDTO = mapStudiosToDTO(studios, normalizedCountriesWithId);

        var normalizedStudiosWithId = fetchStudiosWithId(studiosDTO);

        var newGameStudios = new ArrayList<StudioReturnFullGameInfo>();

        for (CompanyReturnDTO studioData : studios) {
            var normalizedName = normalizeString(studioData.companyName());

            var studio = handleStudioCreationOrFetch(normalizedName, studioData, gameId, normalizedCountriesWithId, normalizedStudiosWithId);

            addGameStudio(newGameStudios, gameId, studio);
        }

        return newGameStudios;
    }

    private Map<String, CountryReturnDTO> processCountryInfo(List<CompanyReturnDTO> studios) {
        List<String> countryNames = studios.stream()
                .map(studio -> studio.countryInfo().name().common())
                .toList();

        List<CountryReturnDTO> countries = countryService.getCountriesByName(countryNames);

        return countries.stream().collect(Collectors.toMap(
                country -> normalizeString(country.name()),
                country -> country
        ));
    }

    private List<StudioDTO> mapStudiosToDTO(List<CompanyReturnDTO> studios, Map<String, CountryReturnDTO> normalizedCountriesWithId) {
        return studios.stream()
                .map(studio -> {
                    var normalizedCompanyName = normalizeString(studio.companyName());
                    var normalizedCountryName = normalizeString(studio.countryInfo().name().common());
                    var country = normalizedCountriesWithId.get(normalizedCountryName);
                    if (country == null) {
                        throw new IllegalArgumentException("País não encontrado no sistema: " + studio.countryInfo().name().common());
                    }
                    return new StudioDTO(normalizedCompanyName, country.id());
                })
                .collect(Collectors.toList());
    }

    private Map<String, StudioReturnDTO> fetchStudiosWithId(List<StudioDTO> studiosDTO) {
        return studioService.getStudiosByName(studiosDTO)
                .stream()
                .collect(Collectors.toMap(
                        studio -> normalizeString(studio.name()),
                        studio -> studio
                ));
    }

    private StudioReturnDTO handleStudioCreationOrFetch(String normalizedName, CompanyReturnDTO studioData,
                                                        String gameId,
                                                        Map<String, CountryReturnDTO> normalizedCountriesWithId,
                                                        Map<String, StudioReturnDTO> normalizedStudiosWithId) {

        StudioReturnDTO studio = normalizedStudiosWithId.get(normalizedName);
        if (studio != null) {
            logger.info("Estúdio '{}' já existe. Associando ao jogo ID: {}", studio.name(), gameId);
            return studio;
        }

        logger.info("Criando novo estúdio '{}'", capitalizeEachWord(normalizedName));

        var studioCountry = normalizeString(studioData.countryInfo().name().common());
        CountryReturnDTO country = normalizedCountriesWithId.get(studioCountry);

        StudioDTO newStudio = new StudioDTO(capitalizeEachWord(normalizedName), country.id());

        return studioService.createStudio(newStudio);
    }

    private void addGameStudio(List<StudioReturnFullGameInfo> newGameStudios, String gameId, StudioReturnDTO studio) {
        var gameStudioDTO = new GameStudioDTO(gameId, studio.id().toString());
        var gameStudioCreated = gameStudioService.createGameStudio(gameStudioDTO);
        newGameStudios.add(new StudioReturnFullGameInfo(gameStudioCreated.id(), gameStudioCreated.studioName()));
    }
}
