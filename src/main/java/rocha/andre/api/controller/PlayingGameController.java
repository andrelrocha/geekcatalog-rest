package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameReturnDTO;
import rocha.andre.api.domain.playingGame.DTO.PlayingGameUpdateDTO;
import rocha.andre.api.service.PlayingGameService;

@RestController
@RequestMapping("/playing")
public class PlayingGameController {
    @Autowired
    private PlayingGameService playingGameService;

    @PostMapping("/add")
    public ResponseEntity<PlayingGameReturnDTO> addPlayingGames(@RequestBody PlayingGameDTO dto) {
        var playingGame = playingGameService.addPlayingGame(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(playingGame);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<PlayingGameReturnDTO>> getPlayingGamesPageable(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "20") int size,
                                                                @RequestParam(defaultValue = "firstPlayed") String sortField,
                                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gamesPageable = playingGameService.getAllPlayingGames(pageable);
        return ResponseEntity.ok(gamesPageable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletePlayingGame(@PathVariable long id) {
        playingGameService.deletePlayingGame(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<PlayingGameReturnDTO> updatePlayingGame(@RequestBody PlayingGameUpdateDTO data) {
        var updatedGame = playingGameService.updatePlayingGame(data);
        return ResponseEntity.ok(updatedGame);
    }
}
