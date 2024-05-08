package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioDTO;
import rocha.andre.api.domain.gameStudio.DTO.GameStudioReturnDTO;
import rocha.andre.api.service.GameGenreService;
import rocha.andre.api.service.GameStudioService;

@RestController
@RequestMapping("/gamestudio")
public class GameStudioController {
    @Autowired
    private GameStudioService service;

    @GetMapping("/bygameid/{gameId}")
    public ResponseEntity<Page<GameStudioReturnDTO>> getGameGenreByGameId(@PathVariable String gameId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size);
        var gamesPageable = service.getAllGameStudiosByGameId(gameId, pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @PostMapping("/create")
    public ResponseEntity<GameStudioReturnDTO> createGameGenre(@RequestBody GameStudioDTO data) {
        var newGameStudio = service.createGameStudios(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGameStudio);
    }
}
