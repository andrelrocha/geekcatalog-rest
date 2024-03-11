package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.dropped.DTO.DroppedDTO;
import rocha.andre.api.domain.dropped.DTO.DroppedReturnDTO;
import rocha.andre.api.domain.dropped.useCase.AddGameToDropped;
import rocha.andre.api.domain.dropped.useCase.GetDropped;
import rocha.andre.api.service.DroppedService;

@Service
public class DroppedServiceImpl implements DroppedService {
    @Autowired
    private AddGameToDropped addGameToDropped;
    @Autowired
    private GetDropped getDropped;

    @Override
    public DroppedReturnDTO addGameToDropped(DroppedDTO data) {
        var droppedGame = addGameToDropped.addGameToDropped(data);
        return droppedGame;
    }

    @Override
    public Page<DroppedReturnDTO> getDroppedGames(Pageable pageable) {
        var droppedGames = getDropped.getDroppedGames(pageable);
        return droppedGames;
    }
}
