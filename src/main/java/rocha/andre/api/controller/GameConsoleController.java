package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleDTO;
import rocha.andre.api.domain.gameConsole.DTO.GameConsoleReturnDTO;
import rocha.andre.api.domain.gameConsole.DTO.UpdateGameConsoleDTO;
import rocha.andre.api.service.GameConsoleService;

@RestController
@RequestMapping("/gameconsole")
@Tag(name = "Game Console Routes Mapped on Controller")
public class GameConsoleController {
    @Autowired
    private GameConsoleService gameConsoleService;

    @GetMapping("/bygameid/{gameId}")
    public ResponseEntity<Page<GameConsoleReturnDTO>> getGameConsolesByGameId(@PathVariable String gameId,
                                                                              @RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "50") int size) {
        var pageable = PageRequest.of(page, size);
        var gamesPageable = gameConsoleService.getAllGameConsolesByGameId(gameId, pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @PostMapping("/create")
    public ResponseEntity<GameConsoleReturnDTO> createGameConsole(@RequestBody GameConsoleDTO data) {
        var newGameConsole = gameConsoleService.createGameConsole(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGameConsole);
    }

    @PutMapping("/update/{gameId}")
    public ResponseEntity<Page<GameConsoleReturnDTO>> updateGameConsole(@RequestBody UpdateGameConsoleDTO data, @PathVariable String gameId) {
        var gameConsoles = gameConsoleService.updateGameConsoles(data, gameId);
        return ResponseEntity.ok(gameConsoles);
    }
}
