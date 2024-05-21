package rocha.andre.api.domain.imageGame_legacy.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record ImageGameLegacyDTO(MultipartFile imageFile, UUID gameId) {
}
