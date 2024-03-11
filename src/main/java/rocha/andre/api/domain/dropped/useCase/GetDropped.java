package rocha.andre.api.domain.dropped.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.dropped.DTO.DroppedReturnDTO;
import rocha.andre.api.domain.dropped.DroppedRepository;


@Component
public class GetDropped {
    @Autowired
    private DroppedRepository repository;

    public Page<DroppedReturnDTO> getDroppedGames(Pageable pageable) {
        return repository.findAllDroppedGames(pageable).map(DroppedReturnDTO::new);
    }

}
