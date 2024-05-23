package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserDTO;
import rocha.andre.api.domain.listPermissionUser.DTO.ListPermissionUserReturnDTO;
import rocha.andre.api.service.ListPermissionUserService;

@RestController
@RequestMapping("/listspermission")
public class ListPermissionUserController {
    @Autowired
    private ListPermissionUserService listPermissionUserService;

    @PostMapping("/add")
    public ResponseEntity<ListPermissionUserReturnDTO> addListPermissionUser(@RequestBody ListPermissionUserDTO data) {
        var newListPermissionUser = listPermissionUserService.addPermissionToUserOnList(data);
        return ResponseEntity.ok(newListPermissionUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteListPermissionUser(@RequestBody ListPermissionUserDTO data) {
        listPermissionUserService.deleteListPermission(data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getall/{listId}")
    public ResponseEntity getAllListsPermissionPageable (@PathVariable String listId,
                                                         @RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var listsPageable = listPermissionUserService.getAllPermissionsByUserAndListID(tokenJWT, listId);
        return ResponseEntity.ok(listsPageable);
    }
}
