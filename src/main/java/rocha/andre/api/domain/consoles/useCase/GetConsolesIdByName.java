package rocha.andre.api.domain.consoles.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.consoles.ConsoleRepository;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetConsolesIdByName {
    @Autowired
    private ConsoleRepository consoleRepository;

    public List<ConsoleReturnDTO> getConsolesByName(ArrayList<ConsoleDTO> data) {
        List<String> names = data.stream()
                .map(ConsoleDTO::name)
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();

        var consoles = consoleRepository.findAllByNamesIgnoreCaseAndTrimmed(names);

        return consoles.stream()
                .map(console -> new ConsoleReturnDTO(console.getId(), console.getName()))
                .toList();
    }
}
