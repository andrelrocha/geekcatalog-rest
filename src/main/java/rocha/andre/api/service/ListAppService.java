package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnWithGameIdsDTO;

public interface ListAppService {
    ListAppReturnDTO createListApp(ListAppDTO data);
    ListAppReturnDTO updateListApp(ListAppDTO data, String listId);
    void deleteList(String listId, String tokenJWT);
    Page<ListAppReturnDTO> getAllListsByUserId(String userId, Pageable pageable);
    Page<ListAppReturnDTO> getAllPublicListsByUserId(String userId, Pageable pageable);
    Page<ListAppReturnDTO> getAllListsWithReadPermission(String userId, Pageable pageable);
    Page<ListAppReturnWithGameIdsDTO> getAllListsByUserIdWithLatestGamesID(String userId, Pageable pageable);
}
