package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.DTO.UserLoginDTO;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.domain.user.*;

@Component
public class CreateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserReturnDTO createUser(UserDTO data) {
        boolean userExists = userRepository.userExistsByLogin(data.login());

        if (userExists) {
            throw new ValidationException("Email on user creation already exists in our database");
        }

        User newUser = new User(data);

        String encodedPassword = bCryptPasswordEncoder.encode(data.password());
        newUser.setPassword(encodedPassword);

        System.out.println(newUser.getLogin());
        System.out.println("chamou antes de salvar");

        var userOnDb = userRepository.save(newUser);

        System.out.println("chamou depois de salvar");
        System.out.println(userOnDb.getId());

        return new UserReturnDTO(userOnDb);
    }
}
