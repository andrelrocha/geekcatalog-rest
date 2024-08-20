package rocha.andre.api.domain.user.DTO;

import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRole;

import java.time.LocalDate;
import java.util.UUID;

public record UserPublicReturnDTO(
                                  String name,
                                  LocalDate birthday,
                                  String countryName,
                                  UUID countryId) {

}
