package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame.DTO.ImageGameDTO;
import rocha.andre.api.domain.imageGame.DTO.ImageGameIdDTO;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.useCase.AddImageGame;
import rocha.andre.api.domain.imageGame.useCase.ReturnAllImageGamesID;
import rocha.andre.api.domain.imageGame.useCase.ReturnAllImageGamesPageable;
import rocha.andre.api.domain.imageGame.useCase.ReturnImageFromGame;
import rocha.andre.api.service.ImageGameService;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageGameServiceImpl implements ImageGameService {
    @Autowired
    private AddImageGame addImageGame;
    @Autowired
    private ReturnAllImageGamesID returnAllImageGamesID;
    @Autowired
    private ReturnAllImageGamesPageable returnAllImageGamesPageable;
    @Autowired
    private ReturnImageFromGame returnImageFromGame;

    @Override
    public ImageGameReturnDTO addImageGame(MultipartFile file, UUID gameId) throws IOException {
        var ImageGameDTO = new ImageGameDTO(file, gameId);
        var imageGame = addImageGame.addImageGame(ImageGameDTO);
        return imageGame;
    }

    @Override
    public byte[] returnImage(UUID gameId) throws Exception {
        var imageDecompressed = returnImageFromGame.returnImage(gameId);
        return imageDecompressed;
    }

    @Override
    public Page<ImageGameIdDTO> returnAllIds(Pageable pageable) {
        var allId = returnAllImageGamesID.returnAllIds(pageable);
        return allId;
    }

    @Override
    public Page<ImageGameReturnDTO> returnAllImages(Pageable pageable) {
        var imageGamesDTO = returnAllImageGamesPageable.returnAllImages(pageable);
        return imageGamesDTO;
    }
}
