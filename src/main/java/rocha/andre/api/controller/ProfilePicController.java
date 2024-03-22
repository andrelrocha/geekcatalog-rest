package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.profilePic.DTO.ProfilePicDTO;
import rocha.andre.api.service.ProfilePicService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/profilepic")
public class ProfilePicController {
    @Autowired
    private ProfilePicService profilePicService;

    @PostMapping("/create/{userId}")
    public ResponseEntity saveProfilePic(@RequestPart("file") MultipartFile file, @PathVariable UUID userId) throws IOException {
        var profilePicDTO = new ProfilePicDTO(file, userId);
        var profilePic = profilePicService.addProfilePic(profilePicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(profilePic);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<byte[]> returnProfilePicByUserId(@PathVariable UUID userId) throws Exception {
        var imageDecompressed = profilePicService.returnProfilePic(userId);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok().headers(headers).body(imageDecompressed);
    }
}
