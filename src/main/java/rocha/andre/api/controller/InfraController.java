package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.infra.security.TokenService;


import java.io.IOException;

@RestController
@RequestMapping("/infra")
@Tag(name = "Infra Routes Mapped on Controller")
public class InfraController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/verifyjwt")
    public boolean isTokenJWTValid(@RequestHeader("Authorization") String authorizationHeader) {
        String tokenJwt = authorizationHeader.substring(7);
        var isValid = tokenService.isAccessTokenValid(tokenJwt);
        if (!isValid) {
            throw new BadCredentialsException("Seu token de autenticação falhou. Você não tem autorização para acessar a aplicação.");
        }

        return true;
    }

    @GetMapping("/ping")
    public String pingServer() {
        return "Servidor está online";
    }

    @GetMapping("/download/apk")
    public ResponseEntity<Resource> downloadApk() throws IOException {
        var resource = new ClassPathResource("files/geekcatalog-v.1.0.2.apk");

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=geekcatalog-v.1.0.2.apk");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
