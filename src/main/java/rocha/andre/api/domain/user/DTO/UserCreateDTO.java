package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.country.Country;

import java.time.LocalDate;

public record UserCreateDTO(UserDTO data, Country country, LocalDate birthday) {
}
