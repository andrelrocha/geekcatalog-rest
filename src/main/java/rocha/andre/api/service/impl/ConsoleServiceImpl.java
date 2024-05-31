package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.consoles.useCase.GetAllConsoles;
import rocha.andre.api.domain.consoles.useCase.GetAllConsolesByGameId;
import rocha.andre.api.service.ConsoleService;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    @Autowired
    private GetAllConsoles getAllConsoles;
    @Autowired
    private GetAllConsolesByGameId getAllConsolesByGameId;

    @Override
    public Page<ConsoleReturnDTO> getAllConsoles(Pageable pageable) {
        var consoles = getAllConsoles.getAllConsoles(pageable);
        return consoles;
    }

    @Override
    public Page<ConsoleReturnDTO> getAllConsolesByGameId(Pageable pageable, String gameId) {
        return getAllConsolesByGameId.getAllConsolesByGameId(pageable, gameId);
    }
}
