package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;

import java.io.IOException;
import java.util.UUID;

public interface ImageGameService {
    ImageGameReturnDTO addImageGame(MultipartFile file, UUID gameId) throws IOException;
    Page<ImageGameReturnDTO> getImageGames(Pageable pageable);
    ImageGameReturnDTO getImageGamesByGameID(String gameId);
}
