package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.domain.listsApp.DTO.ListAppReturnDTO;
import rocha.andre.api.domain.listsApp.useCase.CreateList;
import rocha.andre.api.domain.listsApp.useCase.UpdateList;
import rocha.andre.api.service.ListAppService;

@Service
public class ListAppServiceImpl implements ListAppService {
    @Autowired
    private CreateList createList;
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
}
