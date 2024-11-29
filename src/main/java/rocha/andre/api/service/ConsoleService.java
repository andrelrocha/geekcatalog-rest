package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.consoles.DTO.ConsoleDTO;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;

import java.util.ArrayList;
import java.util.List;

public interface ConsoleService {
    ConsoleReturnDTO createConsole(ConsoleDTO data);
    Page<ConsoleReturnDTO> getAllConsoles(Pageable pageable);
    Page<ConsoleReturnDTO> getAllConsolesByGameId(Pageable pageable, String gameId);
    List<ConsoleReturnDTO> getConsolesByName(ArrayList<ConsoleDTO> data);
}
