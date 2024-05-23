package rocha.andre.api.domain.listsApp.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.ListAppRepository;

import java.util.UUID;

@Component
public class GetListPublicPageable {
    @Autowired
    private ListAppRepository repository;

    public Page<ListAppReturnDTO> getAllPublicListsByUserId(String userId, Pageable pageable) {
        var userIdUUID = UUID.fromString(userId);

        var pageablePublicListsByUserId = repository.findAllListsPublicByUserId(pageable, userIdUUID).map(ListAppReturnDTO::new);

        return pageablePublicListsByUserId;
    }
}
