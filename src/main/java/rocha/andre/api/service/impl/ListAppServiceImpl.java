package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.useCase.*;
import rocha.andre.api.service.ListAppService;

@Service
public class ListAppServiceImpl implements ListAppService {
    @Autowired
    private CreateList createList;
    @Autowired
    private DeleteList deleteList;
    @Autowired
    private GetListByID getListByID;
    @Autowired
    private GetListPageable getListPageable;
    @Autowired
    private GetListWithReadPermissionPageable getListWithReadPermissionPageable;
    @Autowired
    private GetListPublicPageable getListPublicPageable;
    @Autowired
    private UpdateList updateList;

    @Override
    public ListAppReturnDTO createListApp(ListAppDTO data) {
        var list = createList.createListApp(data);
        return list;
    }

    @Override
    public ListAppReturnDTO updateListApp(ListAppDTO data, String listId) {
        var updatedList = updateList.updateListApp(data, listId);
        return updatedList;
    }

    @Override
    public void deleteList(String listId, String tokenJWT) {
        deleteList.deleteList(listId, tokenJWT);
    }

    @Override
    public ListAppReturnDTO getList(String listId) {
        var list = getListByID.getList(listId);
        return list;
    }

    @Override
    public Page<ListAppReturnDTO> getAllListsByUserId(String userId, Pageable pageable) {
        var listPageable = getListPageable.getAllListsByUserId(userId, pageable);
        return listPageable;
    }

    @Override
    public Page<ListAppReturnDTO> getAllPublicListsByUserId(String userId, Pageable pageable) {
        var publicLists = getListPublicPageable.getAllPublicListsByUserId(userId, pageable);
        return publicLists;
    }

    @Override
    public Page<ListAppReturnDTO> getAllListsWithReadPermission(String userId, Pageable pageable) {
        var listsWithReadPermission = getListWithReadPermissionPageable.getAllListsWithReadPermission(userId, pageable);
        return listsWithReadPermission;
    }
}
