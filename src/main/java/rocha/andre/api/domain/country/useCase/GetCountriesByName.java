package rocha.andre.api.domain.country.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.CountryRepository;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;

import java.util.List;

@Component
public class GetCountriesByName {
    @Autowired
    private CountryRepository countryRepository;

    public List<CountryReturnDTO> getCountriesByName(List<String> names) {

        var countries = countryRepository.findAllByNamesIgnoreCaseAndTrimmed(names);

        return countries.stream()
                .map(country -> new CountryReturnDTO(country.getId(), country.getName(), country.getCode()))
                .toList();
    }
}
