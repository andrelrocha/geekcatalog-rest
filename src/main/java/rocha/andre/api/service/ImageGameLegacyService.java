package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame_legacy.DTO.ImageGameLegacyIdDTO;
import rocha.andre.api.domain.imageGame_legacy.DTO.ImageGameReturnLegacyDTO;

import java.io.IOException;
import java.util.UUID;

public interface ImageGameLegacyService {
    ImageGameReturnLegacyDTO addImageGame(MultipartFile file, UUID gameId) throws IOException;

    byte[] returnImage(UUID gameId) throws Exception;
    Page<ImageGameLegacyIdDTO> returnAllIds(Pageable pageable);
    Page<ImageGameReturnLegacyDTO> returnAllImages(Pageable pageable);
}
