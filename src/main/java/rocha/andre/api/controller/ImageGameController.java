package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.service.ImageGameService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageGameController {
    @Autowired
    private ImageGameService imageGameService;

    @PostMapping("/create/{gameId}")
    public ResponseEntity saveImageGame(@RequestPart("file") MultipartFile file, @PathVariable long gameId) throws IOException {
        var imageGame = imageGameService.addImageGame(file, gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageGame);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<byte[]> returnImageByGameId(@PathVariable long gameId) throws Exception {
        var imageDecompressed = imageGameService.returnImage(gameId);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok().headers(headers).body(imageDecompressed);
    }
}
