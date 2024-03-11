package rocha.andre.api.domain.user.DTO;

import jakarta.validation.constraints.NotNull;

public record UserResetPassDTO(
        @NotNull
        String login,
        @NotNull
        String password,
        @NotNull
        String tokenMail
) {
}