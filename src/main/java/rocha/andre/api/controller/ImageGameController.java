package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame.useCase.AddImageGame;
import rocha.andre.api.infra.utils.aws.DependencyFactory;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/imagegame")
public class ImageGameController {

    @Autowired
    private AddImageGame addImageGame;

    @PostMapping("/upload/{gameId}")
    public ResponseEntity uploadImageGame(@RequestPart("file") MultipartFile file, @PathVariable UUID gameId) throws IOException {
        var response = addImageGame.addImageGame(file, gameId);
        return ResponseEntity.ok(response);
    }
}
