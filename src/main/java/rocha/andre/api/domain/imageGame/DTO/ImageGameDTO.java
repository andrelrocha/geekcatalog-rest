package rocha.andre.api.domain.imageGame.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ImageGameDTO(MultipartFile imageFile, UUID gameId) {
}
