package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import rocha.andre.api.domain.user.DTO.UserCreateDTO;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.domain.user.UserTheme;
import rocha.andre.api.infra.security.AccessTokenDTO;
import rocha.andre.api.infra.security.TokenService;
import rocha.andre.api.infra.utils.oauth.google.GoogleUserInfo;
import rocha.andre.api.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@Tag(name = "OAuth Routes Mapped on Controller")
public class OAuthController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @GetMapping("/options")
    public ModelAndView showLoginOptions() {
        ModelAndView modelAndView = new ModelAndView("oauth/oauth-options");
        modelAndView.addObject("client_id", clientId);
        modelAndView.addObject("redirect_uri", redirectUri);

        return modelAndView;
    }

    @GetMapping("/google")
    public ResponseEntity handleGoogleLogin(@RequestParam String code) {
        // Troca o código de autorização pelo access token do google
        String accessToken = exchangeCodeForAccessToken(code);

        // Recupera as informações do usuário a partir do token
        GoogleUserInfo googleUser = getGoogleUserInfo(accessToken);

        User user = userRepository.findByLoginToHandle(googleUser.email());

        if (user == null) {
            UserCreateDTO userCreateDTO = new UserCreateDTO(
                    new UserDTO(
                            googleUser.email(),
                            "N/A", // Senha não aplicável
                            googleUser.name(),
                            "N/A", // CPF não disponível
                            "N/A", // Telefone não disponível
                            null, // Data de aniversário não disponível
                            null, // countryId
                            googleUser.name(), // username
                            false, // twoFactorEnabled
                            false, // refreshTokenEnabled
                            UserTheme.LIGHT.toString() // theme
                    ),
                    null // country, se aplicável
            );
            user = new User(userCreateDTO);
            var userOnDB = userRepository.save(user);
            System.out.println("usuário no banco"+userOnDB);
        }

        // Gera tokens JWT para o usuário autenticado
        String jwtToken = tokenService.generateAccessToken(user);
        AccessTokenDTO accessTokenDto = new AccessTokenDTO(jwtToken);

        return ResponseEntity.ok(accessTokenDto);
    }


    private String exchangeCodeForAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", redirectUri);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://oauth2.googleapis.com/token", params, Map.class
        );
        return (String) response.getBody().get("access_token");
    }

    private GoogleUserInfo getGoogleUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> rawResponse = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET, entity, String.class
        );

        ResponseEntity<GoogleUserInfo> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v3/userinfo",
                HttpMethod.GET, entity, GoogleUserInfo.class
        );
        return response.getBody();
    }
}
