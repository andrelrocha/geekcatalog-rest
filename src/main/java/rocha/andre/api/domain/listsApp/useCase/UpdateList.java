package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class UpdateList {
    @Autowired
    private ListAppRepository repository;

    public ListAppReturnDTO updateListApp(ListAppDTO data, String listId) {
        var listIdUUID = UUID.fromString(listId);
        var userIdUUID = UUID.fromString(data.userId());

        var listApp = repository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrada lista com o id informado no processo de update da lista"));

        if (!listApp.getUser().getId().equals(userIdUUID)) {
            //se o usuário nao é o mesmo da solicitação, faz a query para lists_permission_user para recuperar o ownerId, e então se não tiver, ele estoura o erro
            throw new ValidationException("O usuário que está tentando editar a lista não é o proprietário da mesma e nem tem permissão para a operação.");
        }

        listApp.updateList(data);

        var listAppOnDB = repository.save(listApp);

        return new ListAppReturnDTO(listAppOnDB);
    }
}
