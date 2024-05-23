package rocha.andre.api.domain.listPermissionUser.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.domain.listPermissionUser.ListPermissionUserRepository;
import rocha.andre.api.domain.user.UseCase.GetUserByTokenJWT;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GetListPermissionUserByListAndUser {
    @Autowired
    private ListPermissionUserRepository repository;
    @Autowired
    private GetUserByTokenJWT getUserByTokenJWT;

    public List<ListPermissionUserReturnDTO> getAllPermissionsByUserAndListID(String tokenJWT, String listId) {
        var participant = getUserByTokenJWT.getUserByID(tokenJWT);
        var participantIdUUID = UUID.fromString(participant.id());
        var listIdUUID = UUID.fromString(listId);

        var allPermissionsByListAndUserID = repository.findAllByParticipantIdAndListId(participantIdUUID, listIdUUID)
                .stream()
                .map(ListPermissionUserReturnDTO::new)
                .collect(Collectors.toList());

        return allPermissionsByListAndUserID;
    }

}
