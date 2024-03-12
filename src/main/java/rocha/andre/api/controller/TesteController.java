package rocha.andre.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.DTO.UserLoginDTO;
import rocha.andre.api.domain.user.DTO.UserOnlyLoginDTO;
import rocha.andre.api.domain.user.DTO.UserResetPassDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.service.UserService;

@RestController
@RequestMapping("/teste")
public class TesteController {
    @Autowired
    private UserRepository repository;

    @PostMapping
    public ResponseEntity getTeste(@RequestBody UserLoginDTO data) {
        System.out.println("chamou antes da query");
        var user = repository.findByLoginToHandle(data.login());
        return ResponseEntity.ok(user);
    }
}

