package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame.DTO.ImageGameIdDTO;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;

import java.io.IOException;
import java.util.UUID;

public interface ImageGameService {
    ImageGameReturnDTO addImageGame(MultipartFile file, UUID gameId) throws IOException;

    byte[] returnImage(UUID gameId) throws Exception;
    Page<ImageGameIdDTO> returnAllIds(Pageable pageable);
    Page<ImageGameReturnDTO> returnAllImages(Pageable pageable);
}
