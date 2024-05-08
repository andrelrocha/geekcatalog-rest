package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsoles.DTO.GameConsoleReturnDTO;
import rocha.andre.api.service.GameConsoleService;

@RestController
@RequestMapping("/gameconsole")
public class GameConsoleController {
    @Autowired
    private GameConsoleService gameConsoleService;

    @GetMapping("/bygameid/{gameId}")
    public ResponseEntity<Page<GameConsoleReturnDTO>> getGameConsolesByGameId(@PathVariable String gameId,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size);
        var gamesPageable = gameConsoleService.getAllGameConsolesByGameId(gameId, pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @PostMapping("/create")
    public ResponseEntity<GameConsoleReturnDTO> createGameConsole(@RequestBody GameConsoleDTO data) {
        var newGameConsole = gameConsoleService.createGameConsole(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGameConsole);
    }
}
