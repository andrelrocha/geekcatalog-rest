package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.user.User;

import java.time.LocalDate;

public record UserUpdateDTO(String name,
                            String username,
                            Boolean twoFactorEnabled,
                            String phone,
                            LocalDate birthday,
                            Country country,
                            String theme) {

    public UserUpdateDTO(User user, LocalDate birthday, Country country, String theme) {
        this(user.getName(), user.getUsername(), user.isTwoFactorEnabled(), user.getPhone(), birthday, country, user.getTheme().toString());
    }
}
