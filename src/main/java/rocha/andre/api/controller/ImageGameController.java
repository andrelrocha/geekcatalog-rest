package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.service.ImageGameService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/imagegame")
public class ImageGameController {

    @Autowired
    private ImageGameService imageGameService;

    @PostMapping("/upload/{gameId}")
    public ResponseEntity uploadImageGame(@RequestPart("file") MultipartFile file, @PathVariable UUID gameId) throws IOException {
        var response = imageGameService.addImageGame(file, gameId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity getAllImageGamesPageable ( @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        var pageable = PageRequest.of(page, size);
        var imageGames = imageGameService.getImageGames(pageable);
        return ResponseEntity.ok(imageGames);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity getImageGameByGameId (@PathVariable String gameId) {
        var imageGame = imageGameService.getImageGamesByGameID(gameId);
        return ResponseEntity.ok(imageGame);
    }
}
