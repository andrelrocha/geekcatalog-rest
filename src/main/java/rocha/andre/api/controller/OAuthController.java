package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rocha.andre.api.infra.utils.OAuth.google.GoogleAccessToken;
import rocha.andre.api.infra.security.AccessTokenDTO;
import rocha.andre.api.infra.utils.httpCookies.CookieManager;
import rocha.andre.api.service.OAuthGoogleService;


@RestController
@RequestMapping("/oauth")
@Tag(name = "OAuth Routes Mapped on Controller")
public class OAuthController {
    @Autowired
    private CookieManager cookieManager;
    @Autowired
    private OAuthGoogleService oAuthGoogleService;

    @GetMapping("/options")
    public ModelAndView showLoginOptions() {
        return oAuthGoogleService.showLoginOptions();
    }

    @GetMapping("/google")
    public ResponseEntity<GoogleAccessToken> handleGoogleLogin(@RequestParam String code) {
        String accessToken = oAuthGoogleService.exchangeCodeForAccessToken(code);
        var googleAccessToken = new GoogleAccessToken(accessToken);
        return ResponseEntity.ok(googleAccessToken);
    }

    @GetMapping("/googlelogin")
    public ResponseEntity<AccessTokenDTO> authenticateGoogleUser(@RequestHeader("Authorization") String authorizationHeader, HttpServletResponse response, HttpServletRequest request) {
        var googleAccessToken = authorizationHeader.substring(7);
        var authTokens = oAuthGoogleService.signInGoogleUser(googleAccessToken, request);
        if (authTokens.refreshToken() != null) {
            cookieManager.addRefreshTokenCookie(response, authTokens.refreshToken());
        }
        AccessTokenDTO accessTokenDto = new AccessTokenDTO(authTokens.accessToken());
        return ResponseEntity.ok(accessTokenDto);
    }
}
