package rocha.andre.api.domain.user.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

public record UserDTO(@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "O endereço de e-mail não é válido")
                      @NotNull
                      String login,
                      @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
                      @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).*$", message = "A senha deve conter pelo menos uma letra maiúscula e um número")
                      @NotNull
                      String password,
                      @NotNull String name,
                      @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve seguir o formato 999.999.999-99")
                      @NotNull String cpf,
                      @Pattern(regexp = "\\(\\d{2,3}\\)\\d{5}-\\d{4}", message = "O telefone deve seguir o formato (99)99999-9999")
                      String phone,
                      @JsonFormat(pattern="yyyy-MM-dd")
                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
                      LocalDate birthday,
                      UUID countryId) {
}
