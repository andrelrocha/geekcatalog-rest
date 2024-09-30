package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/verifyjwt/{tokenJwt}")
    public boolean isTokenJWTValid(@PathVariable String tokenJwt) {
        var isValid = tokenService.isJwtTokenValid(tokenJwt);

        if (!isValid) {
            throw new BadCredentialsException("Seu token de autenticação falhou. Você não tem autorização para acessar a aplicação.");
        }

        return true;
    }

    @GetMapping("/oauth")
    public String handleOAuthResponse(@RequestParam(name = "code", required = false) String authorizationCode,
                                      @RequestParam(name = "error", required = false) String error,
                                      @RequestParam(name = "error_description", required = false) String errorDescription,
                                      @RequestParam(name = "redirect_uri", required = false) String redirectUri) {

        if (error != null) {
            System.out.println("Falha na autorização: " + error);
            System.out.println("Descrição do erro: " + errorDescription);
            return "error";
        }

        System.out.println("Código de autorização recebido: " + authorizationCode);
        System.out.println("Redirect URI: " + redirectUri);

        return "success";
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
