package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.infra.security.TokenService;
import rocha.andre.api.service.SpreadsheetService;

import java.io.IOException;

@RestController
@RequestMapping("/infra")
@Tag(name = "Infra Routes Mapped on Controller")
public class InfraController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SpreadsheetService spreadsheetService;

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

    @GetMapping("/download/games/{userId}")
    public ResponseEntity<byte[]> exportGamesOnUserListsToXLS(@PathVariable String userId) {
        var xlsxFile = spreadsheetService.exportGamesOnListRegularUserToXlsx(userId);
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=games_on_list.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(xlsxFile.readAllBytes());
    }

    @GetMapping("/admin/download/games/{userId}")
    public ResponseEntity<byte[]> exportGamesOnUserListsAdminToXLS(@PathVariable String userId) {
        var xlsxFile = spreadsheetService.exportGamesOnListWithIdXlsx(userId);
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=games_on_list.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(xlsxFile.readAllBytes());
    }

    @PostMapping("/admin/import/games/{userId}")
    public ResponseEntity importGamesOnXLSXSheet(@PathVariable String userId, @RequestPart("file") MultipartFile file) {
        if (file.isEmpty() || !file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File format not accepted");
        }
        var savedGames = spreadsheetService.saveNewGameDataOnDB(file, userId);
        return ResponseEntity.status(HttpStatus.OK).body(savedGames);
    }
}
