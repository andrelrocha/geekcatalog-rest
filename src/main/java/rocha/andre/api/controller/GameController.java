package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.game.DTO.GameDTO;
import rocha.andre.api.domain.game.DTO.GameReturnDTO;
import rocha.andre.api.domain.game.DTO.SystemSecretDTO;
import rocha.andre.api.domain.game.Game;
import rocha.andre.api.service.GameService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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

}
