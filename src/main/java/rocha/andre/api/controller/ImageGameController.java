package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame.DTO.ImageGameIdDTO;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.service.ImageGameService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/imagegame")
public class ImageGameController {
    @Autowired
    private ImageGameService imageGameService;

    @PostMapping("/create/{gameId}")
    public ResponseEntity saveImageGame(@RequestPart("file") MultipartFile file, @PathVariable UUID gameId) throws IOException {
        var imageGame = imageGameService.addImageGame(file, gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageGame);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<byte[]> returnImageByGameId(@PathVariable UUID gameId) throws Exception {
        var imageDecompressed = imageGameService.returnImage(gameId);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok().headers(headers).body(imageDecompressed);
    }

    @GetMapping("/allgamesid")
    public ResponseEntity<Page<ImageGameIdDTO>> returnAllGameIds(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "12") int size,
                                                                 @RequestParam(defaultValue = "id") String sortField,
                                                                 @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var allId = imageGameService.returnAllIds(pageable);
        return ResponseEntity.ok(allId);
    }

    @GetMapping("/allgameimages")
    public ResponseEntity<Page<ImageGameReturnDTO>> getAllImageGames(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "6") int size,
                                                                     @RequestParam(defaultValue = "gameId") String sortField,
                                                                     @RequestParam(defaultValue = "asc") String sortOrder) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortField));
        var allImagesPageable = imageGameService.returnAllImages(pageable);
        return ResponseEntity.ok(allImagesPageable);
    }
}
