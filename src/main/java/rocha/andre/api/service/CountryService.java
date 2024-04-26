package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.country.DTO.CountryReturnDTO;

public interface CountryService {
    Page<CountryReturnDTO> getAllCountries(Pageable pageable);
}
