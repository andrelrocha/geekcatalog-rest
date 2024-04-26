package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.country.CountryRepository;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.domain.user.*;

@Component
public class CreateUser {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserReturnDTO createUser(UserDTO data) {
        boolean userExists = userRepository.userExistsByLogin(data.login());

        if (userExists) {
            throw new ValidationException("Email on user creation already exists in our database");
        }

        var country = countryRepository.findById(data.countryId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado país com o id informado"));

        User newUser = new User(data, country);

        String encodedPassword = bCryptPasswordEncoder.encode(data.password());
        newUser.setPassword(encodedPassword);

        var userOnDb = userRepository.save(newUser);

        return new UserReturnDTO(userOnDb);
    }
}
