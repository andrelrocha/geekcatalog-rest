package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;

public interface ListAppService {
    ListAppReturnDTO createListApp(ListAppDTO data);
    ListAppReturnDTO updateListApp(ListAppDTO data, String listId);
    void deleteList(String listId, String tokenJWT);
    Page<ListAppReturnDTO> getAllListsByUserId(String userId, Pageable pageable);
}
