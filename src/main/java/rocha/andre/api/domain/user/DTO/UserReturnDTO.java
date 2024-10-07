package rocha.andre.api.domain.user.DTO;

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
                            String theme,
                            String countryName,
                            String countryId,
                            Boolean twoFactorEnabled,
                            Boolean refreshTokenEnabled,
                            UserRole role) {

    public UserReturnDTO(User user) {
        this(user.getId().toString(), user.getLogin(), user.getUsername(), user.getName(), user.getCpf(), user.getPhone(),
                user.getBirthday(), String.valueOf(user.getTheme()), user.getCountry().getName(), user.getCountry().getId().toString(),
                user.isTwoFactorEnabled(), user.isRefreshTokenEnabled(), user.getRole());
    }
}
