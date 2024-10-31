package rocha.andre.api.domain.consoles.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.Console;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.infra.exceptions.ValidationException;

@Component
public class CreateConsole {
    @Autowired
    private ConsoleRepository consoleRepository;

    public ConsoleReturnDTO createConsole(ConsoleDTO data) {
        var console = consoleRepository.findByName(data.name());

        if (console != null) {
            throw new ValidationException("Já existe console com o nome informado");
        }

        var newConsole = new Console(data);
        var consoleOnDB = consoleRepository.save(newConsole);

        return new ConsoleReturnDTO(consoleOnDB);
    }
}
