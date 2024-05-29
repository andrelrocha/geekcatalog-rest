package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class GetListByID {
    @Autowired
    private ListAppRepository repository;

    public ListAppReturnDTO getList(String listId) {
        var listIUUID = UUID.fromString(listId);
        var list = repository.findById(listIUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrada lista com o id informado"));

        return new ListAppReturnDTO(list);
    }
}
