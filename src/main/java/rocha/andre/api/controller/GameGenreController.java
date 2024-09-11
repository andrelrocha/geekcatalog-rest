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
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.DTO.UpdateGameGenreDTO;
import rocha.andre.api.service.GameConsoleService;
import rocha.andre.api.service.GameGenreService;

@RestController
@RequestMapping("/gamegenre")
@Tag(name = "Game Genre Routes Mapped on Controller")
public class GameGenreController {
    @Autowired
    private GameGenreService service;

    @GetMapping("/bygameid/{gameId}")
    public ResponseEntity<Page<GameGenreReturnDTO>> getGameGenreByGameId(   @PathVariable String gameId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "50") int size) {
        var pageable = PageRequest.of(page, size);
        var gamesPageable = service.getAllGameGenresByGameId(gameId, pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @PostMapping("/create")
    public ResponseEntity<GameGenreReturnDTO> createGameGenre(@RequestBody GameGenreDTO data) {
        var newGameConsole = service.createGameGenre(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGameConsole);
    }

    @PutMapping("/update/{gameId}")
    public ResponseEntity<Page<GameGenreReturnDTO>> updateGameGenre(@RequestBody UpdateGameGenreDTO data, @PathVariable String gameId) {
        var gameGenres = service.updateGameGenres(data, gameId);
        return ResponseEntity.ok(gameGenres);
    }
}
