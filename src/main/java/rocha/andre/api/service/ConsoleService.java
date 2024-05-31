package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;

public interface ConsoleService {
    Page<ConsoleReturnDTO> getAllConsoles(Pageable pageable);
    Page<ConsoleReturnDTO> getAllConsolesByGameId(Pageable pageable, String gameId);
}
