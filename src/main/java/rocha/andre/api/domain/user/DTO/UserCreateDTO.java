package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.theme.Theme;

import java.time.LocalDate;

public record UserCreateDTO(UserDTO data, Theme theme, Country country, LocalDate birthday) {
}
