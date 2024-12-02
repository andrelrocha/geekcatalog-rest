package rocha.andre.api.domain.utils.API.IGDB.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.studios.DTO.StudioDTO;
import rocha.andre.api.domain.utils.API.IGDB.DTO.CompanyReturnDTO;
import rocha.andre.api.service.CountryService;

import java.util.List;
import java.util.Map;

import static rocha.andre.api.infra.utils.stringFormatter.StringFormatter.normalizeString;

@Component
public class StudioDTOFormatterFromIGDB {
    @Autowired
    private StudioCountryMapperFromIGDB studioCountryMapperFromIGDB;
    @Autowired
    private CountryService countryService;

    public List<StudioDTO> mapStudiosToDTO(List<CompanyReturnDTO> studios, Map<String, CountryReturnDTO> normalizedStudiosMap) {
        Map<String, CountryReturnDTO> normalizedCountriesWithId = studioCountryMapperFromIGDB.buildNormalizedStudiosCountryMap(studios);

        return mapStudios(studios, normalizedCountriesWithId);
    }

    private List<StudioDTO> mapStudios(List<CompanyReturnDTO> studios, Map<String, CountryReturnDTO> normalizedCountriesWithId) {
        return studios.stream()
                .map(studio -> {
                    var normalizedCompanyName = normalizeString(studio.companyName());
                    var normalizedCountryName = normalizeString(studio.countryInfo().name().common());
                    var country = normalizedCountriesWithId.get(normalizedCountryName);
                    if (country == null) {
                        throw new IllegalArgumentException("Country not found on system: " + studio.countryInfo().name().common());
                    }
                    return new StudioDTO(normalizedCompanyName, country.id());
                })
                .toList();
    }
}
