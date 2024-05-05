package rocha.andre.api.domain.studios.DTO;

import rocha.andre.api.domain.country.Country;

import java.util.UUID;

public record CreateStudioDTO(String name, Country country) {
}
