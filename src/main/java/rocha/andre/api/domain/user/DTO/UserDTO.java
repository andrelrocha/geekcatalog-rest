package rocha.andre.api.domain.user.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "O endereço de e-mail não é válido")
                      @NotNull
                      String login,
                      @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
                      @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "A senha deve conter pelo menos uma letra maiúscula e um número")
                      @NotNull
                      String password,
                      @NotNull String name,
                      @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve seguir o formato 999.999.999-99")
                      @NotNull String cpf) {
}
