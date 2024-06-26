package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.DTO.DeleteListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUser;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;

@Component
public class DeleteAllListPermissionUser {
    @Autowired
    private ListPermissionUserRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;

    public void deleteAllListPermission(DeleteListPermissionUserDTO data) {
        var participantInvited = userRepository.findByLoginToHandle(data.participantLogin());
        if (participantInvited == null) {
            throw new ValidationException("Não foi encontrado usuário com o login informado como participant no processo de adição de permissões a um usuário sobre uma lista");
        }

        var listIdUUID = UUID.fromString(data.listId());

        var user = getUserByTokenJWT.getUserByID(data.tokenJwt());
        var ownerIdUUID = UUID.fromString(user.id());
        var owner = userRepository.findById(ownerIdUUID)
                .orElseThrow(() -> new ValidationException("Não foi encontrado usuário com o id informado como owner no processo de delete de uma permissao de lista"));

        var listPermissionUser = repository.findAllByListId(listIdUUID);

        if (listPermissionUser.isEmpty()) {
            throw new ValidationException("Não foram encontrados registros de permissão do usuário sobre a lista informada.");
        } else if (!listPermissionUser.get(0).getOwner().equals(owner)) {
            throw new ValidationException("O usuário que está tentando apagar uma permissão não é o dono da lista e não tem permissão para a operação realizada.");
        }

        repository.deleteAll(listPermissionUser);
    }
}
