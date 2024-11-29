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
        var newGameStudios = new ArrayList<StudioReturnFullGameInfo>();

        var normalizedCountriesWithId = processCountryInfo(studios);

        List<StudioDTO> studiosDTO = studios.stream()
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

        Map<String, StudioReturnDTO> normalizedStudiosWithId = studioService.getStudiosByName(studiosDTO)
                .stream().collect(Collectors.toMap(
                        studio -> normalizeString(studio.name()),
                        studio -> studio
                ));


        for (CompanyReturnDTO studioData : studios) {
            var normalizedName = normalizeString(studioData.companyName());
            StudioReturnDTO studio;

            if (normalizedStudiosWithId.containsKey(normalizedName)) {
                studio = normalizedStudiosWithId.get(normalizedName);
            } else {
                var studioCountry = studioData.countryInfo().name().common().toLowerCase().trim();
                var country = normalizedCountriesWithId.get(studioCountry);
                var studioCountryId = country.id();
                var newStudio = new StudioDTO(capitalizeEachWord(normalizedName), studioCountryId);
                studio = studioService.createStudio(newStudio);
            }

            logger.info("Associando estúdio '{}' ao jogo ID: {}", studio.name(), gameId);
            var gameStudioDTO = new GameStudioDTO(gameId, studio.id().toString());
            gameStudioService.createGameStudio(gameStudioDTO);

            newGameStudios.add(new StudioReturnFullGameInfo(studio.id(), studio.name()));
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
}
