package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.domain.consoles.useCase.GetAllConsoles;
import rocha.andre.api.service.ConsoleService;

@Service
public class ConsoleServiceImpl implements ConsoleService {
    @Autowired
    private GetAllConsoles getAllConsoles;


    @Override
    public Page<ConsoleReturnDTO> getAllConsoles(Pageable pageable) {
        var consoles = getAllConsoles.getAllConsoles(pageable);
        return consoles;
    }
}
