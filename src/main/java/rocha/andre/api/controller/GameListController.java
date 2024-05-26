package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.DTO.UpdateGameGenreDTO;
import rocha.andre.api.domain.gameList.DTO.GameListReturnDTO;
import rocha.andre.api.service.GameGenreService;
import rocha.andre.api.service.GameListService;

@RestController
@RequestMapping("/gamelist")
public class GameListController {
    @Autowired
    private GameListService service;

    @GetMapping("/getall/{listId}")
    public ResponseEntity<Page<GameListReturnDTO>> getGameGenreByGameId(@PathVariable String listId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "12") int size) {
        var pageable = PageRequest.of(page, size);
        var gameListPageable = service.getGamesByListID(pageable, listId);
        return ResponseEntity.ok(gameListPageable);
    }

    /*
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
    */
}