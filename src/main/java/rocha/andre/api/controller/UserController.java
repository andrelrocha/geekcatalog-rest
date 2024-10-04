package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.user.DTO.*;
import rocha.andre.api.infra.security.AccessTokenDTO;
import rocha.andre.api.infra.security.AuthTokensDTO;
import rocha.andre.api.service.UserService;

@RestController
@RequestMapping("/user")
@Tag(name = "User Routes Mapped on Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<AccessTokenDTO> performLogin(@RequestBody @Valid UserLoginDTO data, HttpServletResponse response) {
        AuthTokensDTO tokensJwt = userService.performLogin(data);
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokensJwt.refreshToken())
                .httpOnly(true)
                .path("/")
                .maxAge(15 * 24 * 60 * 60) // Validade do refresh token: 15 dias
                .sameSite("Strict") // Proteção contra CSRF
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        AccessTokenDTO accessTokenDto = new AccessTokenDTO(tokensJwt.accessToken());
        return ResponseEntity.ok(accessTokenDto);
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<UserReturnDTO> createUser(@RequestBody @Valid UserDTO data) {
        var newUser = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/bytokenjwt")
    public ResponseEntity<UserReturnDTO> getUserByTokenJWT(@RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        var user = userService.getUserByTokenJWT(tokenJWT);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot_password")
    @Transactional
    public ResponseEntity<String> forgotPassword(@RequestBody UserOnlyLoginDTO data) {
        var stringReturn = userService.forgotPassword(data);
        return ResponseEntity.ok(stringReturn);
    }

    @PostMapping("/reset_password")
    @Transactional
    public ResponseEntity<String> resetPassword(@RequestBody UserResetPassDTO data) {
        var stringSuccess= userService.resetPassword(data);
        return ResponseEntity.ok(stringSuccess);
    }

    @GetMapping("/getuserid/{tokenJwt}")
    public ResponseEntity<UserIdDTO> getUserId(@PathVariable String tokenJwt) {
        var userId = userService.getUserIdByJWT(tokenJwt);
        return ResponseEntity.ok(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<UserReturnDTO> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserGetInfoUpdateDTO data) {
        var tokenJWT = authorizationHeader.substring(7);
        var updatedUser = userService.updateUserInfo(data, tokenJWT);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String authorizationHeader) {
        var tokenJWT = authorizationHeader.substring(7);
        userService.deleteUser(tokenJWT);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public/{userId}")
    public ResponseEntity<UserPublicReturnDTO> getPublicInfo(@PathVariable String userId) {
        var publicInfo = userService.getPublicInfoByUserId(userId);
        return ResponseEntity.ok(publicInfo);
    }
}
