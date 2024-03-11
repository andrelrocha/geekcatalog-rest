package rocha.andre.api.domain.user.DTO;

import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull
        String login,
        @NotNull
        String password
) {  }
