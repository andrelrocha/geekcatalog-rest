package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.user.User;

import java.time.LocalDate;

public record UserUpdateDTO(String name,
                            String phone,
                            LocalDate birthday,
                            Country country) {

    public UserUpdateDTO(User user, LocalDate birthday, Country country) {
        this(user.getName(), user.getPhone(), birthday, country);
    }
}
