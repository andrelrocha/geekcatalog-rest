package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.DTO.ListAppCreateDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.ListApp;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class CreateList {
    @Autowired
    private ListAppRepository listAppRepository;
    @Autowired
    private UserRepository userRepository;

    public ListAppReturnDTO createListApp(ListAppDTO data) {
        var userIdUUID = UUID.fromString(data.userId());
        var existsByNameAndUserId = listAppRepository.existsByName(data.name(), userIdUUID);

        if (existsByNameAndUserId) {
            throw new ValidationException("Já existe uma lista com esse nome cadastrado");
        }

        var user = userRepository.findByIdToHandle(userIdUUID);

        if (user == null) {
            throw new ValidationException("Não foi encontrado usuário com o id informado no processo de criação de lista");
        }

        var listCreateDTO = new ListAppCreateDTO(data.name(), data.description(), data.visibility(), user);

        var listApp = new ListApp(listCreateDTO);

        var listAppOnDB = listAppRepository.save(listApp);

        return new ListAppReturnDTO(listAppOnDB);
    }
}
