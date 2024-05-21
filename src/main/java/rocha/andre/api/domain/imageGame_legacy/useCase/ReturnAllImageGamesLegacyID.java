package rocha.andre.api.domain.imageGame_legacy.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.imageGame_legacy.DTO.ImageGameLegacyIdDTO;
import rocha.andre.api.domain.imageGame_legacy.ImageGameLegacyRepository;

@Component
public class ReturnAllImageGamesLegacyID {
    @Autowired
    private ImageGameLegacyRepository repository;

    public Page<ImageGameLegacyIdDTO> returnAllIds(Pageable pageable) {
        var allIds = repository.findAllGameIdsOrderedByName(pageable).map(ImageGameLegacyIdDTO::new);

        return allIds;
    }

}
