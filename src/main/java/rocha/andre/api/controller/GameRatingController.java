package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.gameRating.DTO.GameRatingDTO;
import rocha.andre.api.service.GameRatingService;

@RestController
@RequestMapping("/gamerating")
public class GameRatingController {
    @Autowired
    private GameRatingService service;

    @PostMapping("/add")
    public ResponseEntity addGameRating(@RequestBody GameRatingDTO data) {
        var gameRating = service.addGameRating(data);
        return ResponseEntity.ok(gameRating);
    }
}
