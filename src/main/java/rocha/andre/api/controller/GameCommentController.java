package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameComment.DTO.CreateGameCommentDTO;
import rocha.andre.api.service.GameCommentService;

@RestController
@RequestMapping("/gamecomment")
public class GameCommentController {
    @Autowired
    private GameCommentService gameCommentService;

    @PostMapping("/add")
    public ResponseEntity addComment(@RequestBody CreateGameCommentDTO data,
                                     @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var gameComment = gameCommentService.addGameComment(data, tokenJWT);
        return ResponseEntity.ok(gameComment);
    }
}
