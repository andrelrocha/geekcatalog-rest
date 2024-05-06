package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.imageGame.DTO.ImageGameDTO;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.ImageGame;
import rocha.andre.api.domain.imageGame.ImageGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.utils.imageCompress.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
public class AddImageGame {
    @Autowired
    private ImageGameRepository imageGameRepository;
    @Autowired
    private GameRepository gameRepository;

    public ImageGameReturnDTO addImageGame(ImageGameDTO dto) throws IOException {
        var game = gameRepository.findById(dto.gameId())
                .orElseThrow(() -> new ValidationException("Não foi encontrado jogo com o id informado."));

        var existsByGameId = imageGameRepository.existsByGameId(game.getId());

        if (existsByGameId) {
            var imageUpdated = updateImage(dto);
            return new ImageGameReturnDTO(imageUpdated);
        }

        var compressedImageBytes = compressImage(dto.imageFile());

        var imageGame = ImageGame.builder()
                .game(game)
                .image(compressedImageBytes)
                .build();

        var imageGameOnDB = imageGameRepository.save(imageGame);

        return new ImageGameReturnDTO(imageGameOnDB);
    }


    private ImageGame updateImage(ImageGameDTO dto) throws IOException {
        var imageGame = imageGameRepository.findImageGameByGameId(dto.gameId());

        var compressedImageBytes = compressImage(dto.imageFile());

        imageGame.updateImage(compressedImageBytes);

        var imageOnDB = imageGameRepository.save(imageGame);

        return imageOnDB;
    }

    public byte[] compressImage(MultipartFile imageFile) throws IOException {
        validateImageFormat(imageFile);

        byte[] compressedImageBytes = ImageUtils.compressImage(imageFile.getBytes());

        if (compressedImageBytes == null) {
            throw new ValidationException("Erro ao comprimir a imagem. O resultado do método compressImage é nulo.");
        }

        return compressedImageBytes;
    }

    private void validateImageFormat(MultipartFile imageFile) throws ValidationException {
        try {
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            if (image == null) {
                throw new ValidationException("A imagem não pôde ser lida ou não é um formato de imagem suportado.");
            }

            if (!"jpeg".equalsIgnoreCase(imageFile.getContentType().substring(imageFile.getContentType().lastIndexOf("/") + 1))) {
                throw new ValidationException("O formato da imagem deve ser JPEG.");
            }
        } catch (IOException e) {
            throw new ValidationException("Erro ao validar o formato da imagem.");
        }
    }
}
