package rocha.andre.api.domain.imageGame.useCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.imageGame.DTO.ImageGameReturnDTO;
import rocha.andre.api.domain.imageGame.ImageGameRepository;
import rocha.andre.api.infra.exceptions.ValidationException;

import java.util.UUID;


@Component
public class GetImageGameByGameID {
    @Autowired
    private ImageGameRepository repository;

    public ImageGameReturnDTO getImageGamesByGameID(String gameId) {
        var gameIdUUID = UUID.fromString(gameId);
        var imageGame = repository.findImageGameByGameID(gameIdUUID);

        if (imageGame == null) {
            throw new ValidationException("Não foi encontrada imagem para o game id informado");
        }
        return new ImageGameReturnDTO(imageGame);
    }
}
