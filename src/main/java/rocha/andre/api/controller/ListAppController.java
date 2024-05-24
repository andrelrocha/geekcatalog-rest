package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.listsApp.DTO.ListAppDTO;
import rocha.andre.api.service.ListAppService;

@RestController
@RequestMapping("/list")
public class ListAppController {
    @Autowired
    private ListAppService listAppService;

    @GetMapping("/getall/{userId}")
    public ResponseEntity getAllListsPageable ( @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "12") int size,
                                                @RequestParam(defaultValue = "name") String sortField,
                                                @RequestParam(defaultValue = "asc") String sortOrder,
                                                @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var listsPageable = listAppService.getAllListsByUserId(userId, pageable);
        return ResponseEntity.ok(listsPageable);
    }

    @GetMapping("/getall/gamesid/{userId}")
    public ResponseEntity getAllListsWithGameIdPageable (   @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "12") int size,
                                                            @RequestParam(defaultValue = "name") String sortField,
                                                            @RequestParam(defaultValue = "asc") String sortOrder,
                                                            @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var listsPageable = listAppService.getAllListsByUserIdWithLatestGamesID(userId, pageable);
        return ResponseEntity.ok(listsPageable);
    }

    @GetMapping("/public/{userId}")
    public ResponseEntity getAllPublicListsPageable (   @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "12") int size,
                                                        @RequestParam(defaultValue = "name") String sortField,
                                                        @RequestParam(defaultValue = "asc") String sortOrder,
                                                        @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var publicListsPageable = listAppService.getAllPublicListsByUserId(userId, pageable);
        return ResponseEntity.ok(publicListsPageable);
    }

    @GetMapping("/public/gamesid/{userId}")
    public ResponseEntity getAllPublicListsWithGameIdPageable (   @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "12") int size,
                                                        @RequestParam(defaultValue = "name") String sortField,
                                                        @RequestParam(defaultValue = "asc") String sortOrder,
                                                        @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var publicListsPageable = listAppService.getAllPublicListsByUserIdWithLatestGamesID(userId, pageable);
        return ResponseEntity.ok(publicListsPageable);
    }

    @GetMapping("/readpermission/{userId}")
    public ResponseEntity getAllListsWithReadPermissionPageable (   @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "12") int size,
                                                                    @RequestParam(defaultValue = "name") String sortField,
                                                                    @RequestParam(defaultValue = "asc") String sortOrder,
                                                                    @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var publicListsPageable = listAppService.getAllListsWithReadPermission(userId, pageable);
        return ResponseEntity.ok(publicListsPageable);
    }

    @GetMapping("/readpermission/gamesid/{userId}")
    public ResponseEntity getAllListsWithReadPermissionWithGameIdPageable (   @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "12") int size,
                                                                    @RequestParam(defaultValue = "name") String sortField,
                                                                    @RequestParam(defaultValue = "asc") String sortOrder,
                                                                    @PathVariable String userId) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var publicListsPageable = listAppService.getAllListsWithReadPermissionWithGamesID(userId, pageable);
        return ResponseEntity.ok(publicListsPageable);
    }

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

    @DeleteMapping("/delete/{listId}")
    public ResponseEntity deleteList(@PathVariable String listId, @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        listAppService.deleteList(listId, tokenJWT);
        return ResponseEntity.noContent().build();
    }

}
