package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.utils.API.IGDB.DTO.IGDBQueryRequestDTO;
import rocha.andre.api.domain.utils.API.Twitch.TwitchAuth;
import rocha.andre.api.domain.utils.fullGame.DTO.CreateFullGameDTO;
import rocha.andre.api.service.IGDBService;
import rocha.andre.api.service.SpreadsheetService;

import java.io.IOException;

@RestController
@RequestMapping("/utils")
@Tag(name = "Utils Routes Mapped on Controller")
public class UtilsController {
    @Autowired
    private SpreadsheetService spreadsheetService;
    @Autowired
    private IGDBService igdbService;
    @Autowired
    private TwitchAuth twitchAuth;

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

    @PostMapping("/igdb/fetch/game")
    public ResponseEntity fetchGameDetails(@RequestBody IGDBQueryRequestDTO request) {
        var queryInfo = twitchAuth.authenticateUser(request.gameName());
        var igbdbResponse = igdbService.fetchGameDetails(queryInfo);
        return ResponseEntity.ok(igbdbResponse);
    }

    @PostMapping("/admin/igdb/creategame")
    public ResponseEntity createGameFromIGDBData(@RequestBody CreateFullGameDTO data) {
        var response = igdbService.createGameFromIGDBInfo(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
