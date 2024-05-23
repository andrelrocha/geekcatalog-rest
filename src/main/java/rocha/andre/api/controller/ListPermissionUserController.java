package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
}
