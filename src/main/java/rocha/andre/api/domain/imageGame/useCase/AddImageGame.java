package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import rocha.andre.api.domain.game.GameRepository;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.ImageGame;
import rocha.andre.api.domain.imageGame.ImageGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;
import rocha.andre.api.infra.utils.aws.DependencyFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


import java.io.IOException;
import java.util.UUID;

@Component
public class AddImageGame {
    @Autowired
    private ImageGameRepository imageGameRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private DependencyFactory dependencyFactory;

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    @Value("${amazon.s3.endpoint}")
    private String endpoint;

    public ImageGameReturnDTO addImageGame(MultipartFile file, UUID gameId) throws IOException {
        var game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ValidationException("No game found with the provided ID."));

        var s3Client = dependencyFactory.s3Client();

        var imageUrl = uploadImageToS3(s3Client, file, gameId);

        var imageGame = ImageGame.builder()
                .game(game)
                .imageUrl(imageUrl)
                .build();

        var existsByGameId = imageGameRepository.existsByGameId(imageGame.getGame().getId());

        if (!existsByGameId) {
            var imageGameOnDB = imageGameRepository.save(imageGame);

            return new ImageGameReturnDTO(imageGameOnDB);
        }

        return new ImageGameReturnDTO(imageGame);
    }

    private String uploadImageToS3(S3Client s3Client, MultipartFile imageBytes, UUID gameId) throws IOException {
        var fileName = "cover-" + gameId + ".jpeg";

        var putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("image/jpeg")
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(imageBytes.getInputStream(), imageBytes.getSize()));

        var endpointImage = endpoint + "/geekcatalog-bucket/" + fileName;

        return endpointImage;
    }
}
