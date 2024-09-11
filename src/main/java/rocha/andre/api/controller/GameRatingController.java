package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndJWTDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingByGameAndUserDTO;
import rocha.andre.api.domain.gameRating.DTO.GameRatingDTO;
import rocha.andre.api.service.GameRatingService;

@RestController
@RequestMapping("/gamerating")
@Tag(name = "Game Rating Routes Mapped on Controller")
public class GameRatingController {
    @Autowired
    private GameRatingService service;

    @PostMapping("/add")
    public ResponseEntity addGameRating(@RequestBody GameRatingDTO data) {
        var gameRating = service.addGameRating(data);
        return ResponseEntity.ok(gameRating);
    }

    @GetMapping("/average/{gameId}")
    public ResponseEntity getAverageGameRating(@PathVariable String gameId) {
        var averageRating = service.getAllRatingsByGameID(gameId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity getGameRatingByUserAndGame(@PathVariable String gameId, @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var data = new GameRatingByGameAndJWTDTO(gameId, tokenJWT);
        var gameRating = service.getRatingByGameAndUser(data);
        return ResponseEntity.ok(gameRating);
    }

    @GetMapping("/average/user/{userId}")
    public ResponseEntity getAverageRatingByUser(@RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var averageRating = service.getAverageRatingByJWT(tokenJWT);
        return ResponseEntity.ok(averageRating);
    }
}
