package rocha.andre.api.domain.country.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.CountryRepository;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;

@Component
public class GetAllCountries {
    @Autowired
    private CountryRepository repository;

    public Page<CountryReturnDTO> getAllCountries(Pageable pageable) {
        var countries = repository.findAllCountriesOrderedByName(pageable).map(CountryReturnDTO::new);
        return countries;
    }
}