package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.dropped.DTO.DroppedDTO;
import rocha.andre.api.domain.dropped.DTO.DroppedReturnDTO;

public interface DroppedService {
    DroppedReturnDTO addGameToDropped(DroppedDTO data);
    Page<DroppedReturnDTO> getDroppedGames(Pageable pageable);
}
