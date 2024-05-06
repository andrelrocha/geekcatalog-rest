package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.imageGame.DTO.ImageGameIdDTO;
import rocha.andre.api.domain.imageGame.ImageGameRepository;



@Component
public class ReturnAllImageGamesID {
    @Autowired
    private ImageGameRepository repository;

    public Page<ImageGameIdDTO> returnAllIds(Pageable pageable) {
        var allIds = repository.findAllGameIdsOrderedByName(pageable).map(ImageGameIdDTO::new);

        return allIds;
    }

}
