package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.finished.DTO.FinishedDTO;
import rocha.andre.api.domain.finished.DTO.FinishedReturnDTO;
import rocha.andre.api.domain.finished.DTO.FinishedUpdateDTO;
import rocha.andre.api.domain.finished.UseCase.*;
import rocha.andre.api.service.FinishedService;

@Service
public class FinishedServiceImpl implements FinishedService {
    @Autowired
    private CreateFinished createFinished;
    @Autowired
    private DeleteFinished deleteFinished;
    @Autowired
    private GetFinished getFinished;
    @Autowired
    private GetFinishedByID getFinishedByID;
    @Autowired
    private UpdateFinished updateFinished;

    public FinishedReturnDTO createFinished(FinishedDTO data) {
        var finished = createFinished.createFinished(data);
        return finished;
    }

    @Override
    public void deleteFinished(long id) {
        deleteFinished.deleteFinished(id);
    }

    @Override
    public Page<FinishedReturnDTO> getFinished(Pageable pageable) {
        var finished = getFinished.getFinished(pageable);
        return finished;
    }

    @Override
    public FinishedReturnDTO getFinishedById(long id) {
        var finishedById = getFinishedByID.getFinishedById(id);
        return finishedById;
    }

    @Override
    public FinishedReturnDTO updateFinished(FinishedUpdateDTO data, long id) {
        var updatedFinished = updateFinished.updateFinished(data, id);
        return updatedFinished;
    }
}
