package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRole;

import java.time.LocalDate;

public record UserReturnDTO(String id,
                            String login,
                            String username,
                            String name,
                            String cpf,
                            String phone,
                            LocalDate birthday,
                            String countryName,
                            String countryId,
                            String themeName,
                            String themeId,
                            Boolean twoFactorEnabled,
                            UserRole role) {

    public UserReturnDTO(User user) {
        this(user.getId().toString(), user.getLogin(), user.getUsername(), user.getName(), user.getCpf(), user.getPhone(),
                user.getBirthday(), user.getCountry().getName(), user.getCountry().getId().toString(), user.getTheme().getName(),
                user.getTheme().getId().toString(), user.isTwoFactorEnabled(), user.getRole());
    }
}
