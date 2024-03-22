package rocha.andre.api.domain.profilePic.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ProfilePicDTO(MultipartFile imageFile, UUID userId) {
}
