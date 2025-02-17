package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioReturnDTO;
import rocha.andre.api.domain.gameStudio.DTO.UpdateGameStudioDTO;
import rocha.andre.api.service.GameStudioService;

@RestController
@RequestMapping("/gamestudio")
@Tag(name = "Game Studio Routes Mapped on Controller")
public class GameStudioController {
    @Autowired
    private GameStudioService service;

    @GetMapping("/bygameid/{gameId}")
    public ResponseEntity<Page<GameStudioReturnDTO>> getGameGenreByGameId(@PathVariable String gameId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "100") int size) {
        var pageable = PageRequest.of(page, size);
        var gamesPageable = service.getAllGameStudiosByGameId(gameId, pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @PostMapping("/create")
    public ResponseEntity<GameStudioReturnDTO> createGameGenre(@RequestBody GameStudioDTO data) {
        var newGameStudio = service.createGameStudio(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGameStudio);
    }

    @PutMapping("/update/{gameId}")
    public ResponseEntity<Page<GameStudioReturnDTO>> updateGameStudio(@RequestBody UpdateGameStudioDTO data, @PathVariable String gameId) {
        var updatedGameStudio  = service.updateGameStudios(data, gameId);
        return ResponseEntity.ok(updatedGameStudio);
    }
}
