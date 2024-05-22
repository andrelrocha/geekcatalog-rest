package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.ListAppRepository;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteList {
    @Autowired
    private ListAppRepository repository;

    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;

    public void deleteList(String listId, String tokenJWT) {
        var listIdUUID = UUID.fromString(listId);
        var listApp = repository.findById(listIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrada lista com o id informado no processo de delete da lista."));

        var user = getUserByTokenJWT.getUserByID(tokenJWT);
        var userIdUUID = UUID.fromString(user.id());

        if (!listApp.getUser().getId().equals(userIdUUID)) {
            throw new ValidationException("O usuário que está tentando apagar a lista não é o proprietário da mesma.");
        }

        repository.delete(listApp);
    }
}
