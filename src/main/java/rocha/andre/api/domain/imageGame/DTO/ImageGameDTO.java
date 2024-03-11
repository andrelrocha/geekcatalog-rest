package rocha.andre.api.domain.imageGame.DTO;

import org.springframework.web.multipart.MultipartFile;

public record ImageGameDTO(MultipartFile imageFile, long gameId) {
}
