package rocha.andre.api.domain.console.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.console.ConsoleRepository;
import rocha.andre.api.domain.console.DTO.ConsoleReturnDTO;

@Component
public class GetAllConsoles {
    @Autowired
    private ConsoleRepository repository;

    public Page<ConsoleReturnDTO> getAllConsoles(Pageable pageable) {
        var consoles = repository.findAllConsolesOrderedByName(pageable).map(ConsoleReturnDTO::new);
        return consoles;
    }
}