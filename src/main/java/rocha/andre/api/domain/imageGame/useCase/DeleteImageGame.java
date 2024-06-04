package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.imageGame.ImageGameRepository;
import rocha.andre.api.infra.utils.aws.DependencyFactory;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.util.UUID;

@Component
public class DeleteImageGame {
    @Autowired
    private ImageGameRepository repository;
    @Autowired
    private DependencyFactory dependencyFactory;

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;

    @Value("${amazon.s3.endpoint}")
    private String endpoint;

    public void deleteImageGameByGameId(String gameId) {
        var gameIdUUID = UUID.fromString(gameId);

        var imageGame = repository.findImageGameByGameID(gameIdUUID);

        if (imageGame == null) {
            throw new RuntimeException("Não existe image game para o id de jogo informado");
        }

        try {
            var s3Client = dependencyFactory.s3Client();

            var deleteRequest = DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(imageGame.getImageUrl())
                            .build();

            s3Client.deleteObject(deleteRequest);

            repository.deleteById(imageGame.getId());
        } catch (Exception e) {
            throw new RuntimeException("Ocorreu um erro ao deletar a imagem de um jogo: "+e.getMessage());
        }
    }
}
