package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame_legacy.DTO.ImageGameLegacyDTO;
import rocha.andre.api.domain.imageGame_legacy.DTO.ImageGameLegacyIdDTO;
import rocha.andre.api.domain.imageGame_legacy.DTO.ImageGameReturnLegacyDTO;
import rocha.andre.api.domain.imageGame_legacy.useCase.AddImageGameLegacy;
import rocha.andre.api.domain.imageGame_legacy.useCase.ReturnAllImageGamesLegacyID;
import rocha.andre.api.domain.imageGame_legacy.useCase.ReturnAllImageGamesPageable;
import rocha.andre.api.domain.imageGame_legacy.useCase.ReturnImageFromGame;
import rocha.andre.api.service.ImageGameLegacyService;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageGameLegacyServiceImpl implements ImageGameLegacyService {
    @Autowired
    private AddImageGameLegacy addImageGameLegacy;
    @Autowired
    private ReturnAllImageGamesLegacyID returnAllImageGamesLegacyID;
    @Autowired
    private ReturnAllImageGamesPageable returnAllImageGamesPageable;
    @Autowired
    private ReturnImageFromGame returnImageFromGame;

    @Override
    public ImageGameReturnLegacyDTO addImageGame(MultipartFile file, UUID gameId) throws IOException {
        var ImageGameDTO = new ImageGameLegacyDTO(file, gameId);
        var imageGame = addImageGameLegacy.addImageGame(ImageGameDTO);
        return imageGame;
    }

    @Override
    public byte[] returnImage(UUID gameId) throws Exception {
        var imageDecompressed = returnImageFromGame.returnImage(gameId);
        return imageDecompressed;
    }

    @Override
    public Page<ImageGameLegacyIdDTO> returnAllIds(Pageable pageable) {
        var allId = returnAllImageGamesLegacyID.returnAllIds(pageable);
        return allId;
    }

    @Override
    public Page<ImageGameReturnLegacyDTO> returnAllImages(Pageable pageable) {
        var imageGamesDTO = returnAllImageGamesPageable.returnAllImages(pageable);
        return imageGamesDTO;
    }
}
