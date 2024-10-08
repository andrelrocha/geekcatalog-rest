package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameComment.DTO.CreateGameCommentDTO;
import rocha.andre.api.service.GameCommentService;

@RestController
@RequestMapping("/gamecomment")
@Tag(name = "Game Comment Routes Mapped on Controller")
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

    @GetMapping("/get/{gameId}")
    public ResponseEntity getCommentsByGameId(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "16") int size,
                                              @RequestParam(defaultValue = "createdAt") String sortField,
                                              @RequestParam(defaultValue = "desc") String sortOrder,
                                              @PathVariable String gameId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gameComment = gameCommentService.getCommentsPageable(pageable, gameId);
        return ResponseEntity.ok(gameComment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity deleteComment(@PathVariable String commentId,
                                        @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        gameCommentService.deleteGameComment(tokenJWT, commentId);
        return ResponseEntity.noContent().build();
    }
}
