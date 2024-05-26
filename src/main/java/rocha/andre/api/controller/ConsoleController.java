package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.consoles.DTO.ConsoleReturnDTO;
import rocha.andre.api.service.ConsoleService;

@RestController
@RequestMapping("/consoles")
public class ConsoleController {
    @Autowired
    private ConsoleService consoleService;

    @GetMapping("/all")
    public ResponseEntity<Page<ConsoleReturnDTO>> getAllConsoles( @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "100") int size,
                                                                  @RequestParam(defaultValue = "name") String sortField,
                                                                  @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var consolesPageable = consoleService.getAllConsoles(pageable);
        return ResponseEntity.ok(consolesPageable);
    }
}
