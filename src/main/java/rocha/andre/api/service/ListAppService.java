package rocha.andre.api.service;

import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;

public interface ListAppService {
    ListAppReturnDTO createListApp(ListAppDTO data);
    ListAppReturnDTO updateListApp(ListAppDTO data, String listId);
    void deleteList(String listId, String tokenJWT);
}
