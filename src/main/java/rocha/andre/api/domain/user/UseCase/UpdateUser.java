package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.CountryRepository;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.user.DTO.UserUpdateDTO;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.security.TokenService;

import java.util.UUID;

@Component
public class UpdateUser {
    @Autowired
    private UserRepository repository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private TokenService tokenService;

    public UserReturnDTO updateUserInfo(UserUpdateDTO data, String tokenJWT) {

        var userId = tokenService.getClaim(tokenJWT);
        userId = userId.replaceAll("\"", "");

        var uuid = UUID.fromString(userId);

        var user = repository.findByIdToHandle(uuid);

        if (user == null) {
            throw new ValidationException("Não existem registros de usuário para o id informado");
        }

        user.updateUser(data);

        if (data.countryId() != null) {
            var countryUuid = UUID.fromString(data.countryId());

            var country = countryRepository.findById(countryUuid)
                    .orElseThrow(() -> new ValidationException("Não foi encontrado país com o id informado no update de user"));

            user.updateCountry(country);
        }

        var userUpdated = repository.save(user);

        return new UserReturnDTO(userUpdated);
    }
}
