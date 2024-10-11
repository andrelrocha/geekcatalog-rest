package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import rocha.andre.api.domain.auditLog.LoginStatus;
import rocha.andre.api.domain.auditLog.useCase.RegisterAuditLog;
import rocha.andre.api.domain.authenticationType.AuthenticationTypeRepository;
import rocha.andre.api.domain.user.DTO.UserCreateDTO;
import rocha.andre.api.domain.user.DTO.UserDTO;
import rocha.andre.api.domain.user.User;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.domain.user.UserTheme;
import rocha.andre.api.domain.userAuthenticationType.DTO.CreateUserAuthenticationTypeDTO;
import rocha.andre.api.domain.userAuthenticationType.UserAuthenticationType;
import rocha.andre.api.domain.userAuthenticationType.UserAuthenticationTypeRepository;
import rocha.andre.api.infra.security.AccessTokenDTO;
import rocha.andre.api.infra.security.TokenService;
import rocha.andre.api.infra.utils.httpCookies.CookieManager;
import rocha.andre.api.infra.utils.oauth.google.GoogleUserInfo;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/oauth")
@Tag(name = "OAuth Routes Mapped on Controller")
public class OAuthController {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 20;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationTypeRepository authenticationTypeRepository;
    @Autowired
    private UserAuthenticationTypeRepository userAuthenticationTypeRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RegisterAuditLog registerAuditLog;
    @Autowired
    private CookieManager cookieManager;
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
        ModelAndView modelAndView = new ModelAndView("oauth-options");
        modelAndView.addObject("client_id", clientId);
        modelAndView.addObject("redirect_uri", redirectUri);

        return modelAndView;
    }

    @GetMapping("/google")
    public ResponseEntity handleGoogleLogin(@RequestParam String code) {
        String accessToken = exchangeCodeForAccessToken(code);
        var googleAccessToken = new GoogleAccessToken(accessToken);
        return ResponseEntity.ok(googleAccessToken);
    }

    @GetMapping("/googlelogin")
    public ResponseEntity authenticateGoogleUser(@RequestHeader("Authorization") String authorizationHeader, HttpServletResponse response, HttpServletRequest request) {
        var googleAccessToken = authorizationHeader.substring(7);
        GoogleUserInfo googleUser = getGoogleUserInfo(googleAccessToken);

        //aqui é para checar pelo oauth_id, ou seja, deve ser criada nova coluna para usuário
        //User user = userRepository.findByLoginToHandle(googleUser.email());

        var user = userAuthenticationTypeRepository.findUserByOAuthId(googleUser.sub());

        if (user == null) {
            var newUsername = generateUniqueUsername(googleUser.email());

            var userCreateDTO = new UserCreateDTO(
                    new UserDTO(
                            googleUser.email(),
                            "N/A",
                            googleUser.name(),
                            "N/A", // CPF não disponível
                            null, // Telefone não disponível
                            null, // Data de aniversário não disponível
                            null, // countryId
                            newUsername,
                            false, // twoFactorEnabled
                            false, // refreshTokenEnabled
                            UserTheme.LIGHT.toString()
                    ),
                    null // country, se aplicável
            );

            user = new User(userCreateDTO);

            String encodedPassword = bCryptPasswordEncoder.encode(generateUniquePassword());
            user.setPassword(encodedPassword);

            var userOnDB = userRepository.save(user);

            var authenticationTypeGoogle = authenticationTypeRepository.findByName("google");

            var userAuthTypeDTO = new CreateUserAuthenticationTypeDTO(authenticationTypeGoogle, userOnDB, googleUser.sub());

            var userAuthType = new UserAuthenticationType(userAuthTypeDTO);

            userAuthenticationTypeRepository.save(userAuthType);
        }

        if (user.isRefreshTokenEnabled()) {
            var refreshToken = tokenService.generateRefreshToken(user);
            cookieManager.addRefreshTokenCookie(response, refreshToken);
        }

        String jwtToken = tokenService.generateAccessToken(user);
        AccessTokenDTO accessTokenDto = new AccessTokenDTO(jwtToken);

        registerAuditLog.logLogin(
                user.getLogin(),
                request,
                LoginStatus.SUCCESS,
                request.getHeader("User-Agent"),
                //codigo do login social pelo google no banco
                UUID.fromString("1c8b6843-b182-49c8-9083-3bf35cc50870")
        );

        return ResponseEntity.ok(accessTokenDto);
    }

    public String generateUniqueUsername(String email) {
        var username = email.trim().substring(0, email.indexOf("@"));
        int counter = 0;

        while (userRepository.userExistsByUsername(username)) {
            counter++;
            username = username + String.format("%03d", counter);
        }

        return username;
    }

    public static String generateUniquePassword() {
        var random = new SecureRandom();
        var password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
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
