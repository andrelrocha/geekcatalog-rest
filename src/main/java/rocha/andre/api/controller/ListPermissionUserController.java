package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.listPermissionUser.DTO.DeleteListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionBulkAddDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.service.ListPermissionUserService;

@RestController
@RequestMapping("/listspermission")
@Tag(name = "Lists Permission Routes Mapped on Controller")
public class ListPermissionUserController {
    @Autowired
    private ListPermissionUserService listPermissionUserService;

    @PostMapping("/add")
    public ResponseEntity<ListPermissionUserReturnDTO> addListPermissionUser(@RequestBody ListPermissionUserDTO data) {
        var newListPermissionUser = listPermissionUserService.addPermissionToUserOnList(data);
        return ResponseEntity.ok(newListPermissionUser);
    }

    @PostMapping("/add/bulk")
    public ResponseEntity addBulkListPermissionUser(@RequestBody ListPermissionBulkAddDTO data) {
        var newListsPermissionUser = listPermissionUserService.addBulkPermissionToUserOnList(data);
        return ResponseEntity.ok(newListsPermissionUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteListPermissionUser(@RequestBody ListPermissionUserDTO data) {
        listPermissionUserService.deleteListPermission(data);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteall/list/{listId}/login/{participantLogin}")
    public ResponseEntity deleteAllListPermissionUser(@PathVariable String listId, @PathVariable String participantLogin,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var data = new DeleteListPermissionUserDTO(listId, participantLogin, tokenJWT);
        listPermissionUserService.deleteAllListPermission(data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all/{listId}")
    public ResponseEntity getAllListsPermissionPageable (@PathVariable String listId,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var listsPageable = listPermissionUserService.getAllPermissionsByUserAndListID(tokenJWT, listId);
        return ResponseEntity.ok(listsPageable);
    }
}
