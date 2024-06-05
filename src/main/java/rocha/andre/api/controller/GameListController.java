package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreDTO;
import rocha.andre.api.domain.gameGenre.DTO.GameGenreReturnDTO;
import rocha.andre.api.domain.gameGenre.DTO.UpdateGameGenreDTO;
import rocha.andre.api.domain.gameList.DTO.*;
import rocha.andre.api.service.GameGenreService;
import rocha.andre.api.service.GameListService;

import java.util.ArrayList;

@RestController
@RequestMapping("/gamelist")
public class GameListController {
    @Autowired
    private GameListService service;

    @GetMapping("/all/{listId}")
    public ResponseEntity<Page<GameListUriReturnDTO>> getGameListByListId(@PathVariable String listId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "12") int size) {
        var pageable = PageRequest.of(page, size);
        var gameListPageable = service.getGamesByListID(pageable, listId);
        return ResponseEntity.ok(gameListPageable);
    }

    @GetMapping("/latest/{listId}")
    public ResponseEntity getLatestGamesPageable (  @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "4") int size,
                                                    @PathVariable String listId) {
        var pageable = PageRequest.of(page, size);
        var gamesPageable = service.getLatestGamesByListID(pageable, listId);
        return ResponseEntity.ok(gamesPageable);
    }

    @GetMapping("/byid/{id}")
    public ResponseEntity<GameListFullReturnDTO> getGameListById(@PathVariable String id) {
        var gameList = service.getGameListByID(id);
        return ResponseEntity.ok(gameList);
    }

    @GetMapping("/gameinfo/{gameListId}")
    public ResponseEntity<GameListGameAndConsolesDTO> getGameInfoByGameListID(@PathVariable String gameListId) {
        var gameList = service.getGameInfoByGameListID(gameListId);
        return ResponseEntity.ok(gameList);
    }

    @GetMapping("/count/{listId}")
    public ResponseEntity<CountGameListReturnDTO> countGameListByListID(@PathVariable String listId) {
        var count = service.countGamesByListID(listId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/add/bulk")
    public ResponseEntity<ArrayList<GameListBulkReturnDTO>> addBulkGameList(@RequestBody GameListBulkCreateDTO data) {
        var newGamesList = service.addBulkGamesToList(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGamesList);
    }

    @PostMapping("/add")
    public ResponseEntity<GameListFullReturnDTO> addGameList(@RequestBody GameListDTO data) {
        var newGameList = service.addGameList(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newGameList);
    }

    @PutMapping("/update/{gameListId}")
    public ResponseEntity<GameListFullReturnDTO> updateGameList(@PathVariable String gameListId,
                                                                @RequestBody GameListUpdateRequestDTO data) {
        var updatedGameList = service.updateGameList(data, gameListId);
        return ResponseEntity.ok(updatedGameList);
    }

    @DeleteMapping("/delete/{gameListId}")
    public ResponseEntity deleteGameList(@PathVariable String gameListId, @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var deleteDTO = new DeleteGameListDTO(gameListId, tokenJWT);
        service.deleteGameList(deleteDTO);
        return ResponseEntity.noContent().build();
    }
}
