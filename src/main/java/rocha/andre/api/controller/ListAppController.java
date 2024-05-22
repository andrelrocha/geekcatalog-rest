package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.service.ListAppService;

@RestController
@RequestMapping("/list")
public class ListAppController {
    @Autowired
    private ListAppService listAppService;


    /*
    @GetMapping("/getall")
    public ResponseEntity getAllGamesPageable ( @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "16") int size,
                                                @RequestParam(defaultValue = "name") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var gamesPageable = gameService.getAllGames(pageable);
        return ResponseEntity.ok(gamesPageable);
    }
    */

    @PostMapping("/create")
    public ResponseEntity createList(@RequestBody ListAppDTO data) {
        var newList = listAppService.createListApp(data);
        return ResponseEntity.ok(newList);
    }


    @PutMapping("/update/{listId}")
    public ResponseEntity updateList(@RequestBody ListAppDTO data, @PathVariable String listId) {
        var updatedList = listAppService.updateListApp(data, listId);
        return ResponseEntity.ok(updatedList);
    }

    /*
    @DeleteMapping("/delete/{gameId}")
    public ResponseEntity deleteList(@PathVariable string gameId) {

    
    }
     */
}
