package rocha.andre.api.domain.user.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record UserGetInfoUpdateDTO(String name,
                                   String phone,
                                   @JsonFormat(pattern = "dd/MM/yyyy")
                                    @DateTimeFormat(pattern = "dd/MM/yyyy")
                                    LocalDate birthday,
                                   String countryId) {
}
