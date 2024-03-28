package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.user.DTO.UserReturnDTO;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetUserByID {
    @Autowired
    private UserRepository repository;

    public UserReturnDTO getUserByID(String id) {
        var uuid = UUID.fromString(id);

        var user = repository.findById(uuid)
                .orElseThrow(() -> new ValidationException("Não foi encontrado usuário com o id informado"));

        return new UserReturnDTO(user);
    }
}
