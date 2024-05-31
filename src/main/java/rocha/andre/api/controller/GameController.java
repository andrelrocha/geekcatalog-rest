package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;


    @GetMapping("/all")
    public ResponseEntity getAllGamesPageable ( @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "16") int size,
                                                @RequestParam(defaultValue = "name") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gamesPageable = gameService.getAllGames(pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @GetMapping("/all/id")
    public ResponseEntity getAllGamesWithIDPageable (@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "" + Integer.MAX_VALUE) int size,
                                                     @RequestParam(defaultValue = "name") String sortField,
                                                     @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gamesPageable = gameService.getAllGamesWithID(pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @PostMapping("/create")
    public ResponseEntity createGame(@RequestBody GameDTO data) {
        var newGame = gameService.createGame(data);
        return ResponseEntity.ok(newGame);
    }

    @PutMapping("/update/{gameId}")
    public ResponseEntity updateGame(@RequestBody GameDTO data, @PathVariable String gameId) {
        var updatedGame = gameService.updateGame(data, gameId);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity deleteGame(@PathVariable String gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
