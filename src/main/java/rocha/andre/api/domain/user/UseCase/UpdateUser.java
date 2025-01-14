package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.Country;
import rocha.andre.api.domain.country.CountryRepository;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.user.DTO.UserGetInfoUpdateDTO;
import rocha.andre.api.domain.user.DTO.UserUpdateDTO;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.security.TokenService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class UpdateUser {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TokenService tokenService;

    public UserReturnDTO updateUserInfo(UserGetInfoUpdateDTO dto, String tokenJWT) {

        var userId = tokenService.getIdClaim(tokenJWT);
        userId = userId.replaceAll("\"", "");

        var uuid = UUID.fromString(userId);

        var user = repository.findByIdToHandle(uuid);

        if (user == null) {
            throw new ValidationException("No User was found for the provided ID.");
        }

        Country country = null;
        if (dto.countryId() != null) {
            var countryUuid = UUID.fromString(dto.countryId());

            country = countryRepository.findById(countryUuid)
                    .orElseThrow(() -> new ValidationException("No country was found fot the informed ID, during user update."));
        }

        LocalDate formattedBirthday = null;
        if (dto.birthday() != null) {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedBirthday = LocalDate.parse(dto.birthday().format(formatter));
        }

        var data = new UserUpdateDTO(dto.name(), dto.username(), dto.twoFactorEnabled(), dto.refreshTokenEnabled(), dto.phone(), formattedBirthday, country, dto.theme());

        user.updateUser(data);

        var userUpdated = repository.save(user);

        return new UserReturnDTO(userUpdated);
    }
}
