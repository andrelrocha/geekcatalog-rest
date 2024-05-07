package rocha.andre.api.domain.user.DTO;

import java.time.LocalDate;

public record UserUpdateDTO(String name, String phone, LocalDate birthday, String countryId) {
}
