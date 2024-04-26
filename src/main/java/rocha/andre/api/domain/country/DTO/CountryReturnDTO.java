package rocha.andre.api.domain.country.DTO;

import rocha.andre.api.domain.country.Country;

import java.util.UUID;

public record CountryReturnDTO(UUID id, String name, String code) {
    public CountryReturnDTO(Country country) {
        this(country.getId(), country.getName(), country.getCode());
    }
}