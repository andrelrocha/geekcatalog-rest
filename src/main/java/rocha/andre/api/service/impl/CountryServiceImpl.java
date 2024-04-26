package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;
import rocha.andre.api.domain.country.useCase.GetAllCountries;
import rocha.andre.api.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private GetAllCountries getAllCountries;


    @Override
    public Page<CountryReturnDTO> getAllCountries(Pageable pageable) {
        var countries = getAllCountries.getAllCountries(pageable);
        return countries;
    }
}
