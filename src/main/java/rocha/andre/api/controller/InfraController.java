package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.infra.security.SecurityFilter;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.infra.security.TokenService;

@RestController
@RequestMapping("/infra")
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
}
