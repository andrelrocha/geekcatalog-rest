package rocha.andre.api.domain.finished.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.finished.DTO.FinishedReturnDTO;
import rocha.andre.api.domain.finished.FinishedRepository;

@Component
public class GetFinished {
    @Autowired
    private FinishedRepository repository;

    public Page<FinishedReturnDTO> getFinished(Pageable pageable) {
        return repository.findAllFinished(pageable).map(FinishedReturnDTO::new);
    }
}
