package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping("/searchbyname/{nameCompare}")
    public ResponseEntity getGamesByName(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "25") int size,
                                                                @RequestParam(defaultValue = "name") String sortField,
                                                                @RequestParam(defaultValue = "asc") String sortOrder,
                                                                @PathVariable String nameCompare) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gamesPageable = 0;
        return ResponseEntity.ok(gamesPageable);
    }

    @GetMapping("/getall")
    public ResponseEntity getAllGamesPageable ( @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "15") int size,
                                                @RequestParam(defaultValue = "name") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gamesPageable = gameService.getAllGames(pageable);
        return ResponseEntity.ok(gamesPageable);
    }
}
