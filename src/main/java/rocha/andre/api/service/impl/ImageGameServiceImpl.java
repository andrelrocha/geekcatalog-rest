package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.imageGame.DTO.ImageGameDTO;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.useCase.AddImageGame;
import rocha.andre.api.domain.imageGame.useCase.ReturnImageFromGame;
import rocha.andre.api.service.ImageGameService;

import java.io.IOException;

@Service
public class ImageGameServiceImpl implements ImageGameService {
    @Autowired
    private AddImageGame addImageGame;
    @Autowired
    private ReturnImageFromGame returnImageFromGame;

    @Override
    public ImageGameReturnDTO addImageGame(MultipartFile file, long gameId) throws IOException {
        var ImageGameDTO = new ImageGameDTO(file, gameId);
        var imageGame = addImageGame.addImageGame(ImageGameDTO);
        return imageGame;
    }

    @Override
    public byte[] returnImage(long gameId) throws Exception {
        var imageDecompressed = returnImageFromGame.returnImage(gameId);
        return imageDecompressed;
    }
}
